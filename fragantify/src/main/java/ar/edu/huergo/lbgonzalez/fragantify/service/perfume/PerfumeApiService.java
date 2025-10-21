package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.PerfumeExternalDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.cache.FragranceCacheEntry;
import ar.edu.huergo.lbgonzalez.fragantify.repository.perfume.FragranceCacheRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerfumeApiService {

    @Value("${fragella.api.key}")
    private String apiKey;

    @Value("${fragella.api.key-header:x-api-key}")
    private String apiKeyHeader;

    @Value("${fragella.api.fragrances-path:/fragrances}")
    private String fragrancesPath;

    @Value("${fragella.api.timeout-seconds:10}")
    private long timeoutSeconds;

    private static final Set<String> ALLOWED_KEYS = Set.of("name", "brand", "gender");
    private static final Set<String> ALLOWED_GENDERS = Set.of("men", "women", "unisex");

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final FragranceCacheRepository cacheRepository;
    private final FragranceStorageService storageService;
    private final ResourceLoader resourceLoader;

    private static final String FALLBACK_RESOURCE = "classpath:fragrances/fragrances-sample.json";

    public List<FragranceDTO> buscarDetalle(Map<String, String> filtros) {
        Map<String, String> params = sanitizarFiltros(filtros);
        return invocarApi(params);
    }

    public List<PerfumeExternalDTO> buscarExternos(Map<String, String> filtros) {
        return buscarDetalle(filtros).stream()
                .map(this::toExternalDTO)
                .filter(dto -> dto != null)
                .toList();
    }

    private Map<String, String> sanitizarFiltros(Map<String, String> filtros) {
        Map<String, String> params = filtros == null ? new HashMap<>() : new HashMap<>(filtros);

        List<String> invalidKeys = params.keySet().stream()
                .filter(key -> !ALLOWED_KEYS.contains(key))
                .collect(Collectors.toList());
        if (!invalidKeys.isEmpty()) {
            throw new IllegalArgumentException("Parametros no permitidos: " + String.join(", ", invalidKeys));
        }

        if (params.containsKey("gender")) {
            String gender = params.get("gender");
            if (gender != null && !gender.isBlank()) {
                String normalized = gender.trim().toLowerCase(Locale.ROOT);
                if (!ALLOWED_GENDERS.contains(normalized)) {
                    throw new IllegalArgumentException("Valor de 'gender' invalido. Admitidos: men, women, unisex");
                }
                params.put("gender", normalized);
            }
        }

        params.replaceAll((key, value) -> value == null ? null : value.trim());
        return params;
    }

    private List<FragranceDTO> invocarApi(Map<String, String> params) {
        try {
            Mono<String> response = webClient.get()
                    .uri(uriBuilder -> {
                        var builder = uriBuilder.path(fragrancesPath);
                        params.forEach((key, value) -> {
                            if (value != null && !value.isBlank()) {
                                builder.queryParam(key, value);
                            }
                        });
                        return builder.build();
                    })
                    .accept(MediaType.APPLICATION_JSON)
                    .header(apiKeyHeader, apiKey)
                    .exchangeToMono(clientResponse -> {
                        var status = clientResponse.statusCode();
                        Mono<String> bodyMono = clientResponse.bodyToMono(String.class)
                                .defaultIfEmpty("");
                        if (status.is2xxSuccessful()) {
                            return bodyMono;
                        }
                        return bodyMono.flatMap(body -> {
                            log.warn("Fragella API respondio {}. Payload: {}", status.value(), preview(body));
                            return Mono.error(new IllegalStateException("Fragella API respondio status " + status.value()));
                        });
                    });

            String payload = response.block(resolveTimeout());
            if (payload == null || payload.isBlank()) {
                log.warn("Fragella API devolvio un cuerpo vacio.");
                return cargarDesdeCacheOFallback(params);
            }
            List<FragranceDTO> resultados = parseResponse(payload);
            if (!resultados.isEmpty() && params.isEmpty()) {
                guardarEnCache(payload);
                storageService.replaceAll(resultados);
            }
            return resultados;
        } catch (Exception ex) {
            log.warn("No se pudo consultar la API externa de fragancias: {}", ex.getMessage());
            return cargarDesdeCacheOFallback(params);
        }
    }

    private List<FragranceDTO> parseResponse(String payload) {
        try {
            JsonNode root = objectMapper.readTree(payload);
            JsonNode dataNode = extractDataNode(root);
            if (dataNode == null || !dataNode.isArray()) {
                log.warn("Formato desconocido de la API de Fragella. Fragmento: {}", preview(payload));
                return Collections.emptyList();
            }

            List<FragranceDTO> resultados = new ArrayList<>();
            for (JsonNode item : dataNode) {
                try {
                    resultados.add(objectMapper.treeToValue(item, FragranceDTO.class));
                } catch (Exception mappingError) {
                    log.debug("No se pudo mapear un item de Fragella: {}", mappingError.getMessage());
                }
            }
            return resultados;
        } catch (Exception parseError) {
            log.warn("No se pudo interpretar la respuesta de Fragella: {}", parseError.getMessage());
            return Collections.emptyList();
        }
    }

    private JsonNode extractDataNode(JsonNode root) {
        if (root == null) {
            return null;
        }
        if (root.isArray()) {
            return root;
        }
        if (root.hasNonNull("data")) {
            JsonNode data = root.get("data");
            if (data.isArray()) {
                return data;
            }
            if (data.hasNonNull("items")) {
                return data.get("items");
            }
        }
        if (root.hasNonNull("items")) {
            return root.get("items");
        }
        return null;
    }

    private Duration resolveTimeout() {
        long seconds = timeoutSeconds > 0 ? timeoutSeconds : 10L;
        return Duration.ofSeconds(seconds);
    }

    private String preview(String body) {
        if (body == null) {
            return "";
        }
        return body.length() <= 200 ? body : body.substring(0, 200) + "...";
    }

    private void guardarEnCache(String payload) {
        try {
            cacheRepository.deleteAll();
            cacheRepository.save(FragranceCacheEntry.builder()
                    .payload(payload)
                    .createdAt(LocalDateTime.now())
                    .build());
        } catch (Exception ex) {
            log.debug("No se pudo almacenar la respuesta de Fragella en cache: {}", ex.getMessage());
        }
    }

    private List<FragranceDTO> cargarDesdeCacheOFallback(Map<String, String> filtros) {
        Optional<FragranceCacheEntry> entryOpt = cacheRepository.findTopByOrderByCreatedAtDesc();
        if (entryOpt.isPresent()) {
            var entry = entryOpt.get();
            List<FragranceDTO> cached = parseResponse(entry.getPayload());
            if (!cached.isEmpty()) {
                List<FragranceDTO> filtrados = aplicarFiltros(cached, filtros);
                log.info("Usando datos cacheados de Fragella ({} registros, generado {}).", cached.size(), entry.getCreatedAt());
                return filtrados;
            }
        }
        List<FragranceDTO> persisted = storageService.loadAll();
        if (persisted != null && !persisted.isEmpty()) {
            log.info("Usando {} fragancias almacenadas en repositorio local.", persisted.size());
            return aplicarFiltros(persisted, filtros);
        }
        List<FragranceDTO> fallback = cargarDesdeRecurso();
        if (fallback.isEmpty()) {
            return Collections.emptyList();
        }
        return aplicarFiltros(fallback, filtros);
    }

    private List<FragranceDTO> cargarDesdeRecurso() {
        try {
            Resource resource = resourceLoader.getResource(FALLBACK_RESOURCE);
            if (!resource.exists()) {
                return Collections.emptyList();
            }
            try (var inputStream = resource.getInputStream()) {
                String payload = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                List<FragranceDTO> datos = parseResponse(payload);
                if (!datos.isEmpty()) {
                    guardarEnCache(payload);
                    storageService.replaceAll(datos);
                    log.info("Fragancias de respaldo cargadas desde recurso local ({} entradas).", datos.size());
                }
                return datos;
            }
        } catch (Exception ex) {
            log.warn("No se pudo leer el archivo de respaldo de fragancias: {}", ex.getMessage());
            return Collections.emptyList();
        }
    }

    private List<FragranceDTO> aplicarFiltros(List<FragranceDTO> source, Map<String, String> filtros) {
        if (source == null || source.isEmpty()) {
            return Collections.emptyList();
        }
        if (filtros == null || filtros.isEmpty()) {
            return source;
        }
        return source.stream()
                .filter(item -> filtra(item, filtros))
                .toList();
    }

    private boolean filtra(FragranceDTO item, Map<String, String> filtros) {
        if (item == null) {
            return false;
        }
        for (Map.Entry<String, String> entry : filtros.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value == null || value.isBlank()) {
                continue;
            }
            String normalizedValue = value.trim().toLowerCase(Locale.ROOT);
            switch (key) {
                case "name" -> {
                    if (item.getName() == null || !item.getName().toLowerCase(Locale.ROOT).contains(normalizedValue)) {
                        return false;
                    }
                }
                case "brand" -> {
                    if (item.getBrand() == null || !item.getBrand().toLowerCase(Locale.ROOT).contains(normalizedValue)) {
                        return false;
                    }
                }
                case "gender" -> {
                    if (item.getGender() == null || !item.getGender().equalsIgnoreCase(normalizedValue)) {
                        return false;
                    }
                }
                default -> {
                    // ignorar filtros desconocidos
                }
            }
        }
        return true;
    }

    private PerfumeExternalDTO toExternalDTO(FragranceDTO fragrance) {
        if (fragrance == null) {
            return null;
        }
        return PerfumeExternalDTO.builder()
                .nombre(fragrance.getName())
                .marca(fragrance.getBrand())
                .precio(fragrance.getPrice())
                .imagenUrl(fragrance.getImageUrl())
                .genero(fragrance.getGender())
                .build();
    }
}

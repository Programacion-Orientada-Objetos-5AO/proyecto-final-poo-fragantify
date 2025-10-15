package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.PerfumeExternalDTO;
import reactor.core.publisher.Mono;

@Service
public class PerfumeApiService {

    @Value("${fragella.api.key}")
    private String apiKey;

    @Value("${fragella.api.key-header:x-api-key}")
    private String apiKeyHeader;

    @Value("${fragella.api.fragrances-path:/fragrances}")
    private String fragrancesPath;

    private static final Set<String> ALLOWED_KEYS = Set.of(
        "name", "brand", "gender"
    );

    private static final Set<String> ALLOWED_GENDERS = new HashSet<>(Arrays.asList(
        "men", "women", "unisex"
    ));

    @Autowired
    private WebClient webClient;

    public List<PerfumeExternalDTO> buscarExternos(Map<String, String> filtros) {
        Map<String, String> params = (filtros != null) ? new HashMap<>(filtros) : new HashMap<>();

        // Validar y filtrar solo las claves permitidas
        List<String> invalidKeys = params.keySet().stream()
            .filter(k -> !ALLOWED_KEYS.contains(k))
            .collect(Collectors.toList());
        if (!invalidKeys.isEmpty()) {
            throw new IllegalArgumentException("Parametros no permitidos: " + String.join(", ", invalidKeys));
        }

        // Normalizar y validar algunos valores especificos
        if (params.containsKey("gender")) {
            String g = params.get("gender");
            if (g != null) {
                String normalized = g.toLowerCase(Locale.ROOT).trim();
                if (!ALLOWED_GENDERS.contains(normalized)) {
                    throw new IllegalArgumentException("Valor de 'gender' invalido. Admitidos: men, women, unisex");
                }
                // Mantener tal cual o capitalizar segun requiera la API; aqui lo dejamos normalizado
                params.put("gender", normalized);
            }
        }

        Mono<FragranceDTO[]> response = webClient.get()
            .uri(uriBuilder -> {
                var builder = uriBuilder.path(fragrancesPath);
                params.forEach((key, value) -> {
                    if (value != null && !value.isBlank()) {
                        builder.queryParam(key, value);
                    }
                });
                return builder.build();
            })
            .header(apiKeyHeader, apiKey)
            .retrieve()
            .bodyToMono(FragranceDTO[].class);

        FragranceDTO[] items = response.block();
        if (items == null || items.length == 0) {
            return new ArrayList<>();
        }

        return Arrays.stream(items)
            .map(this::toExternalDTO)
            .collect(Collectors.toList());
    }

    private PerfumeExternalDTO toExternalDTO(FragranceDTO f) {
        if (f == null) return null;
        return PerfumeExternalDTO.builder()
            .nombre(f.getName())
            .marca(f.getBrand())
            .precio(f.getPrice())
            .imagenUrl(f.getImageUrl())
            .genero(f.getGender())
            .build();
    }
}

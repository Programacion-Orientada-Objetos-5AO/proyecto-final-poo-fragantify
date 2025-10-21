package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceExternalDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceExternalDTO.NoteDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceExternalDTO.NotesDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceExternalDTO.RankingItemDTO;
import reactor.core.publisher.Mono;

@lombok.extern.slf4j.Slf4j
@Service
public class PerfumeApiService {

    @Value("${fragella.api.key}")
    private String apiKey;

    @Value("${fragella.api.key-header:x-api-key}")
    private String apiKeyHeader;

    @Value("${fragella.api.fragrances-path:/fragrances}")
    private String fragrancesPath;

    @Value("classpath:data/fragella-sample.json")
    private Resource fallbackData;

    private static final Set<String> ALLOWED_KEYS = Set.of(
        "name", "brand", "gender"
    );

    private static final Set<String> ALLOWED_GENDERS = new HashSet<>(Arrays.asList(
        "men", "women", "unisex"
    ));

    @Autowired
    private WebClient webClient;

    @Autowired
    private ObjectMapper objectMapper;

    public List<FragranceExternalDTO> buscarExternos(Map<String, String> filtros) {
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

        try {
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
        } catch (Exception ex) {
            log.warn("Fallo al consultar la API externa de Fragella. Se usara el dataset local de respaldo.", ex);
            return buscarEnFallback(params);
        }
    }

    private FragranceExternalDTO toExternalDTO(FragranceDTO fragrance) {
        if (fragrance == null) {
            return null;
        }
        return FragranceExternalDTO.builder()
            .name(fragrance.getName())
            .brand(fragrance.getBrand())
            .price(fragrance.getPrice())
            .imageUrl(fragrance.getImageUrl())
            .imageFallbacks(fragrance.getImageFallbacks())
            .gender(fragrance.getGender())
            .longevity(formatPercentage(fragrance.getLongevity()))
            .sillage(formatPercentage(fragrance.getSillage()))
            .generalNotes(fragrance.getGeneralNotes())
            .mainAccords(fragrance.getMainAccords())
            .mainAccordsPercentage(fragrance.getMainAccordsPercentage())
            .notes(toNotesDTO(fragrance.getNotes()))
            .purchaseUrl(fragrance.getPurchaseUrl())
            .seasonRanking(toRankingItems(fragrance.getSeasonRanking()))
            .occasionRanking(toRankingItems(fragrance.getOccasionRanking()))
            .build();
    }

    private String formatPercentage(Integer value) {
        return value == null ? null : value + "%";
    }

    private NotesDTO toNotesDTO(FragranceDTO.Notes notes) {
        if (notes == null) {
            return null;
        }
        return NotesDTO.builder()
            .top(toNoteDTOList(notes.getTop()))
            .middle(toNoteDTOList(notes.getMiddle()))
            .base(toNoteDTOList(notes.getBase()))
            .build();
    }

    private List<NoteDTO> toNoteDTOList(List<FragranceDTO.NoteItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
            .filter(java.util.Objects::nonNull)
            .map(item -> NoteDTO.builder()
                .name(item.getName())
                .imageUrl(item.getImageUrl())
                .build())
            .collect(Collectors.toList());
    }

    private List<RankingItemDTO> toRankingItems(List<FragranceDTO.RankingItem> items) {
        if (items == null) {
            return null;
        }
        return items.stream()
            .filter(java.util.Objects::nonNull)
            .map(item -> RankingItemDTO.builder()
                .name(item.getName())
                .score(item.getScore())
                .build())
            .collect(Collectors.toList());
    }

    private List<FragranceExternalDTO> buscarEnFallback(Map<String, String> params) {
        if (fallbackData == null || !fallbackData.exists()) {
            return new ArrayList<>();
        }

        try (InputStream inputStream = fallbackData.getInputStream()) {
            FragranceDTO[] items = objectMapper.readValue(inputStream, FragranceDTO[].class);
            return Arrays.stream(items)
                .filter(item -> coincideConFiltros(item, params))
                .map(this::toExternalDTO)
                .collect(Collectors.toList());
        } catch (IOException ioEx) {
            log.error("No fue posible leer el dataset local de fragancias.", ioEx);
            return new ArrayList<>();
        }
    }

    private boolean coincideConFiltros(FragranceDTO item, Map<String, String> filtros) {
        if (item == null) {
            return false;
        }

        if (tieneFiltro(filtros, "name")) {
            String nombreFiltro = filtros.get("name").toLowerCase(Locale.ROOT);
            String nombre = item.getName() != null ? item.getName().toLowerCase(Locale.ROOT) : "";
            if (!nombre.contains(nombreFiltro)) {
                return false;
            }
        }

        if (tieneFiltro(filtros, "brand")) {
            String marcaFiltro = filtros.get("brand").toLowerCase(Locale.ROOT);
            String marca = item.getBrand() != null ? item.getBrand().toLowerCase(Locale.ROOT) : "";
            if (!marca.contains(marcaFiltro)) {
                return false;
            }
        }

        if (tieneFiltro(filtros, "gender")) {
            String genderFiltro = filtros.get("gender").toLowerCase(Locale.ROOT);
            String gender = item.getGender() != null ? item.getGender().toLowerCase(Locale.ROOT) : "";
            if (!gender.equals(genderFiltro)) {
                return false;
            }
        }

        return true;
    }

    private boolean tieneFiltro(Map<String, String> filtros, String key) {
        return filtros.containsKey(key) && filtros.get(key) != null && !filtros.get(key).isBlank();
    }
}

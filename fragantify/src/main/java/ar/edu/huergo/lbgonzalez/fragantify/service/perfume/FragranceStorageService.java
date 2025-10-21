package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.ExternalFragrance;
import ar.edu.huergo.lbgonzalez.fragantify.repository.perfume.ExternalFragranceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FragranceStorageService {

    private final ExternalFragranceRepository repository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void replaceAll(List<FragranceDTO> items) {
        if (items == null || items.isEmpty()) {
            return;
        }
        List<ExternalFragrance> records = items.stream()
                .map(this::toEntity)
                .toList();
        repository.deleteAllInBatch();
        repository.saveAll(records);
        log.info("Se almacenaron {} fragancias desde la API externa.", records.size());
    }

    @Transactional(readOnly = true)
    public List<FragranceDTO> loadAll() {
        return repository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    private ExternalFragrance toEntity(FragranceDTO dto) {
        String externalId = slugify(dto.getBrand(), dto.getName());
        return ExternalFragrance.builder()
                .externalId(externalId)
                .name(dto.getName())
                .brand(dto.getBrand())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .gender(dto.getGender())
                .longevity(dto.getLongevity())
                .sillage(dto.getSillage())
                .generalNotesJson(toJson(dto.getGeneralNotes()))
                .mainAccordsJson(toJson(dto.getMainAccords()))
                .mainAccordsPercentageJson(toJson(dto.getMainAccordsPercentage()))
                .rawPayload(toJson(dto))
                .syncedAt(LocalDateTime.now())
                .build();
    }

    private FragranceDTO toDto(ExternalFragrance entity) {
        if (entity.getRawPayload() != null) {
            try {
                return objectMapper.readValue(entity.getRawPayload(), FragranceDTO.class);
            } catch (Exception ex) {
                log.debug("No se pudo reconstruir el DTO desde rawPayload: {}", ex.getMessage());
            }
        }
        FragranceDTO dto = new FragranceDTO();
        dto.setName(entity.getName());
        dto.setBrand(entity.getBrand());
        dto.setPrice(entity.getPrice());
        dto.setImageUrl(entity.getImageUrl());
        dto.setGender(entity.getGender());
        dto.setLongevity(entity.getLongevity());
        dto.setSillage(entity.getSillage());
        dto.setGeneralNotes(fromJsonArray(entity.getGeneralNotesJson()));
        dto.setMainAccords(fromJsonArray(entity.getMainAccordsJson()));
        dto.setMainAccordsPercentage(fromJsonMap(entity.getMainAccordsPercentageJson()));
        return dto;
    }

    private List<String> fromJsonArray(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (Exception ex) {
            log.debug("No se pudo leer lista desde JSON: {}", ex.getMessage());
            return null;
        }
    }

    private java.util.Map<String, Double> fromJsonMap(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructMapType(java.util.Map.class, String.class, Double.class));
        } catch (Exception ex) {
            log.debug("No se pudo leer mapa desde JSON: {}", ex.getMessage());
            return null;
        }
    }

    private String slugify(String brand, String name) {
        String base = ((brand == null ? "unknown" : brand) + "-" + (name == null ? "fragrance" : name))
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        return base.isBlank() ? "fragrance-" + System.currentTimeMillis() : base;
    }

    private String toJson(Object value) {
        if (value == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(value);
        } catch (Exception ex) {
            log.debug("No se pudo serializar el valor a JSON: {}", ex.getMessage());
            return null;
        }
    }
}

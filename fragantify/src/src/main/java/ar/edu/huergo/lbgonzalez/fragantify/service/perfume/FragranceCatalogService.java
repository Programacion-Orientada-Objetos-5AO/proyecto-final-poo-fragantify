package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.FragranceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FragranceCatalogService {

    private final PerfumeApiService perfumeApiService;
    private final FragranceMapper fragranceMapper;

    public List<FragranceResponseDTO> obtenerCatalogo(Map<String, String> filtros) {
        List<FragranceResponseDTO> externos = cargarDesdeApi(filtros);
        if (externos.isEmpty()) {
            log.warn("La API de Fragella devolvio una lista vacia. Verifica los filtros utilizados.");
        }
        return externos;
    }

    private List<FragranceResponseDTO> cargarDesdeApi(Map<String, String> filtros) {
        try {
            return perfumeApiService.buscarDetalle(filtros).stream()
                    .map(fragranceMapper::fromExternal)
                    .filter(dto -> dto != null)
                    .toList();
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            log.warn("Fallo al consultar la API externa de fragancias: {}", ex.getMessage());
            return List.of();
        }
    }
}

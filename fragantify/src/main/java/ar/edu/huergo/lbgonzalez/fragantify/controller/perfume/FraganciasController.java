package ar.edu.huergo.lbgonzalez.fragantify.controller.perfume;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.service.perfume.FragranceCatalogService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fragancias")
@RequiredArgsConstructor
public class FraganciasController {

    private final FragranceCatalogService fragranceCatalogService;

    @GetMapping
    public ResponseEntity<List<FragranceResponseDTO>> listar(@RequestParam Map<String, String> filtros) {
        List<FragranceResponseDTO> catalogo = fragranceCatalogService.obtenerCatalogo(filtros);
        return ResponseEntity.ok(catalogo);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> manejarArgumentoInvalido(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }
}

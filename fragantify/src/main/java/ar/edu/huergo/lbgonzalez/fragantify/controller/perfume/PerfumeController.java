package ar.edu.huergo.lbgonzalez.fragantify.controller.perfume;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.CrearActualizarPerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.MostrarPerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.PerfumeExternalDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.PerfumeMapper;
import ar.edu.huergo.lbgonzalez.fragantify.service.perfume.PerfumeService;
import ar.edu.huergo.lbgonzalez.fragantify.service.perfume.PerfumeApiService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    @Autowired private PerfumeService perfumeService;
    @Autowired private PerfumeApiService perfumeApiService;
    @Autowired private ar.edu.huergo.lbgonzalez.fragantify.repository.perfume.FragranceJsonRepository jsonRepository;
    @Autowired private PerfumeMapper perfumeMapper;


    @GetMapping
    public ResponseEntity<List<MostrarPerfumeDTO>> obtenerTodosLosPerfumes() {
        List<Perfume> perfumes = perfumeService.getPerfumes();
        return ResponseEntity.ok(perfumeMapper.toDTOList(perfumes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MostrarPerfumeDTO> obtenerPerfumePorId(@PathVariable Long id) {
        Perfume perfume = perfumeService.getPerfume(id)
            .orElseThrow(() -> new RuntimeException("Perfume no encontrado con id " + id));
        return ResponseEntity.ok(perfumeMapper.toDTO(perfume));
    }

   


    @PostMapping
    public ResponseEntity<MostrarPerfumeDTO> crearPerfume(@Valid @RequestBody CrearActualizarPerfumeDTO perfumeDto) {
        Perfume perfume = perfumeMapper.toEntity(perfumeDto);
        Perfume perfumeCreado = perfumeService.crearPerfume(perfume);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(perfumeCreado.getId()).toUri();
        return ResponseEntity.created(location).body(perfumeMapper.toDTO(perfumeCreado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MostrarPerfumeDTO> actualizarPerfume(@PathVariable Long id,
            @Valid @RequestBody CrearActualizarPerfumeDTO perfumeDto) {
        Perfume perfume = perfumeMapper.toEntity(perfumeDto);
        Perfume perfumeActualizado = perfumeService.actualizarPerfume(id, perfume);
        return ResponseEntity.ok(perfumeMapper.toDTO(perfumeActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfume(@PathVariable Long id) {
        perfumeService.eliminarPerfume(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<MostrarPerfumeDTO>> filtrarPerfumes(
            @RequestParam(required = false) String familiaOlfativa,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        List<Perfume> perfumes = perfumeService.filtrarPerfumes(familiaOlfativa, precioMin, precioMax);
        return ResponseEntity.ok(perfumeMapper.toDTOList(perfumes));
    }

    // Busca perfumes en la API externa, validando que los filtros sean correctos
    @GetMapping("/externos")
    public ResponseEntity<List<PerfumeExternalDTO>> buscarPerfumesExternos(@RequestParam(required = false) java.util.Map<String, String> filtros) {
        List<PerfumeExternalDTO> resultados = perfumeApiService.buscarExternos(filtros);
        return ResponseEntity.ok(resultados);
    }

    // Admin: reemplazar el dataset JSON offline con una lista de fragancias
    // Protegido por SecurityConfig: POST /api/perfumes/** requiere rol ADMIN
    @PostMapping("/externos/import")
    public ResponseEntity<Void> importarFraganciasOffline(@RequestBody List<FragranceDTO> items) {
        jsonRepository.replaceAll(items);
        return ResponseEntity.noContent().build();
    }

    // Admin: realiza bulk sync desde la API externa y fusiona con el JSON existente
    // Ej: POST /api/perfumes/externos/sync/bulk?strategy=page&maxPages=50&pageSize=100
    @PostMapping("/externos/sync/bulk")
    public ResponseEntity<java.util.Map<String, Object>> bulkSync(
            @RequestParam(defaultValue = "page") String strategy,
            @RequestParam(defaultValue = "50") int maxPages,
            @RequestParam(defaultValue = "100") int pageSize) {
        int fetched = perfumeApiService.bulkSync(strategy, maxPages, pageSize);
        return ResponseEntity.ok(java.util.Map.of(
                "strategy", strategy,
                "maxPages", maxPages,
                "pageSize", pageSize,
                "fetched", fetched
        ));
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<MostrarPerfumeDTO>> obtenerPerfumesPorMarca(@PathVariable String marca) {
        List<Perfume> perfumes = perfumeService.obtenerPerfumesPorMarca(marca);
        return ResponseEntity.ok(perfumeMapper.toDTOList(perfumes));
    }

    @PostMapping("/comparar")
    public ResponseEntity<List<MostrarPerfumeDTO>> compararPerfumes(@RequestBody List<Long> ids) {
        List<Perfume> perfumes = perfumeService.compararPerfumes(ids);
        return ResponseEntity.ok(perfumeMapper.toDTOList(perfumes));
    }

    @PutMapping("/{id}/favorito")
    public ResponseEntity<MostrarPerfumeDTO> toggleFavorito(@PathVariable Long id) {
        Perfume perfume = perfumeService.toggleFavorito(id);
        return ResponseEntity.ok(perfumeMapper.toDTO(perfume));
    }
}

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
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.MostrarPerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.PerfumeMapper;
import ar.edu.huergo.lbgonzalez.fragantify.service.api.FragellaFragrancesService;
import ar.edu.huergo.lbgonzalez.fragantify.service.perfume.PerfumeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    @Autowired private PerfumeService perfumeService;
    @Autowired private PerfumeMapper perfumeMapper;

    // 👇 Inyectá el service externo (no lo llames estático)
    @Autowired private FragellaFragrancesService fragellaFragrancesService;

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

   @GetMapping("/externos")
    public ResponseEntity<List<FragranceDTO>> buscarExternos(
        @RequestParam(name = "search") String search,
        @RequestParam(name = "limit", required = false) Integer limit) {

    List<FragranceDTO> result = fragellaFragrancesService.searchFragrances(search, limit);
    return ResponseEntity.ok(result);
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

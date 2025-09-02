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

import ar.edu.huergo.lbgonzalez.fragantify.dto.PerfumeDto.*;
import ar.edu.huergo.lbgonzalez.fragantify.dto.PerfumeDto.CrearActualizarPerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.PerfumeDto.MostrarPerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.PerfumeMapper;
import ar.edu.huergo.lbgonzalez.fragantify.service.PerfumeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/Perfumes")
public class PerfumeController {
    @Autowired
    private PerfumeService PerfumeService;

    @Autowired
    private PerfumeMapper PerfumeMapper;

    @GetMapping
    public ResponseEntity<List<MostrarPerfumeDTO>> obtenerTodosLosPerfumes() {
        List<Perfume> Perfumes = PerfumeService.obtenerTodosLosPerfumes();
        List<MostrarPerfumeDTO> PerfumesDTO = PerfumeMapper.toDTOList(Perfumes);
        return ResponseEntity.ok(PerfumesDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MostrarPerfumeDTO> obtenerPerfumePorId(@PathVariable Long id) {
        Perfume Perfume = PerfumeService.obtenerPerfumePorId(id);
        MostrarPerfumeDTO PerfumeDTO = PerfumeMapper.toDTO(Perfume);
        return ResponseEntity.ok(PerfumeDTO);
    }

    @PostMapping
    public ResponseEntity<MostrarPerfumeDTO> crearPerfume(@Valid @RequestBody CrearActualizarPerfumeDTO PerfumeDTO) {
        Perfume Perfume = PerfumeMapper.toEntity(PerfumeDTO);
        Perfume PerfumeCreado = PerfumeService.crearPerfume(Perfume, null);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(PerfumeCreado.getId()).toUri();
        return ResponseEntity.created(location).body(PerfumeMapper.toDTO(PerfumeCreado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MostrarPerfumeDTO> actualizarPerfume(@PathVariable Long id,
            @Valid @RequestBody CrearActualizarPerfumeDTO PerfumeDTO) {
        Perfume Perfume = PerfumeMapper.toEntity(PerfumeDTO);
        Perfume PerfumeActualizado =
                PerfumeService.actualizarPerfume(id, Perfume, null);
        return ResponseEntity.ok(PerfumeMapper.toDTO(PerfumeActualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfume(@PathVariable Long id) {
        PerfumeService.eliminarPerfume(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<MostrarPerfumeDTO>> filtrarPerfumes(
            @RequestParam(required = false) String familiaOlfativa,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        List<Perfume> perfumes = PerfumeService.filtrarPerfumes(familiaOlfativa, precioMin, precioMax);
        List<MostrarPerfumeDTO> perfumesDTO = PerfumeMapper.toDTOList(perfumes);
        return ResponseEntity.ok(perfumesDTO);
    }

    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<MostrarPerfumeDTO>> obtenerPerfumesPorMarca(@PathVariable String marca) {
        List<Perfume> perfumes = PerfumeService.obtenerPerfumesPorMarca(marca);
        List<MostrarPerfumeDTO> perfumesDTO = PerfumeMapper.toDTOList(perfumes);
        return ResponseEntity.ok(perfumesDTO);
    }

    @PostMapping("/comparar")
    public ResponseEntity<List<MostrarPerfumeDTO>> compararPerfumes(@RequestBody List<Long> ids) {
        List<Perfume> perfumes = PerfumeService.compararPerfumes(ids);
        List<MostrarPerfumeDTO> perfumesDTO = PerfumeMapper.toDTOList(perfumes);
        return ResponseEntity.ok(perfumesDTO);
    }

    @PutMapping("/{id}/favorito")
    public ResponseEntity<MostrarPerfumeDTO> toggleFavorito(@PathVariable Long id) {
        Perfume perfume = PerfumeService.toggleFavorito(id);
        MostrarPerfumeDTO perfumeDTO = PerfumeMapper.toDTO(perfume);
        return ResponseEntity.ok(perfumeDTO);
    }
}

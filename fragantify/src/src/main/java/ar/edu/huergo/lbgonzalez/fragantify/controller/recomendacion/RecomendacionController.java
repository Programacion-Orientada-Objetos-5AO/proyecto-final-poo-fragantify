package ar.edu.huergo.lbgonzalez.fragantify.controller.recomendacion;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.lbgonzalez.fragantify.dto.recomendacion.CrearActualizarRecomendacionDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.recomendacion.MostrarRecomendacionDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.recomendacion.Recomendacion;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.recomendacion.RecomendacionMapper;
import ar.edu.huergo.lbgonzalez.fragantify.service.recomendaciones.RecomendacionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recomendaciones")
@Validated
public class RecomendacionController {

    @Autowired private RecomendacionService service;
    @Autowired private RecomendacionMapper mapper;

    @GetMapping
    public ResponseEntity<List<MostrarRecomendacionDTO>> listar() {
        return ResponseEntity.ok(mapper.toDTOList(service.listar()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MostrarRecomendacionDTO> obtener(@PathVariable Long id) {
        Recomendacion r = service.obtener(id)
            .orElseThrow(() -> new RuntimeException("Recomendación no encontrada: " + id));
        return ResponseEntity.ok(mapper.toDTO(r));
    }

    @PostMapping
    public ResponseEntity<MostrarRecomendacionDTO> crear(@Valid @RequestBody CrearActualizarRecomendacionDTO dto) {
        Recomendacion entity = mapper.toEntity(dto);
        Recomendacion creada = service.crear(entity, dto.getCuentaId(), dto.getPerfumeId());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(creada.getId()).toUri();
        return ResponseEntity.created(location).body(mapper.toDTO(creada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MostrarRecomendacionDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody CrearActualizarRecomendacionDTO dto) {
        Recomendacion data = mapper.toEntity(dto);
        Recomendacion actualizada = service.actualizar(id, data, dto.getCuentaId(), dto.getPerfumeId());
        return ResponseEntity.ok(mapper.toDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // filtros
    @GetMapping("/cuenta/{cuentaId}")
    public ResponseEntity<List<MostrarRecomendacionDTO>> porCuenta(@PathVariable Long cuentaId) {
        return ResponseEntity.ok(mapper.toDTOList(service.porCuenta(cuentaId)));
    }

    @GetMapping("/perfume/{perfumeId}")
    public ResponseEntity<List<MostrarRecomendacionDTO>> porPerfume(@PathVariable Long perfumeId) {
        return ResponseEntity.ok(mapper.toDTOList(service.porPerfume(perfumeId)));
    }
}

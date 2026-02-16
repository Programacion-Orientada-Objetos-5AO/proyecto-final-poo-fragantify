package ar.edu.huergo.lbgonzalez.fragantify.controller.tareas;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.lbgonzalez.fragantify.dto.tareas.ResumenCreadorDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.tareas.TareaRequestDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.tareas.TareaResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.tareas.Tarea;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.tareas.TareaMapper;
import ar.edu.huergo.lbgonzalez.fragantify.service.tareas.TareaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tareas")
public class TareaController {

    @Autowired
    private TareaService tareaService;

    @Autowired
    private TareaMapper tareaMapper;

    @GetMapping
    public ResponseEntity<List<TareaResponseDTO>> listar() {
        List<Tarea> tareas = tareaService.listar();
        return ResponseEntity.ok(tareaMapper.toDTOList(tareas));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> obtener(@PathVariable Long id) {
        Tarea tarea = tareaService.buscar(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id " + id));
        return ResponseEntity.ok(tareaMapper.toDTO(tarea));
    }

    @PostMapping
    public ResponseEntity<TareaResponseDTO> crear(@Valid @RequestBody TareaRequestDTO dto) {
        Tarea nueva = tareaMapper.toEntity(dto);
        Tarea guardada = tareaService.crear(nueva);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(guardada.getId()).toUri();
        return ResponseEntity.created(location).body(tareaMapper.toDTO(guardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TareaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody TareaRequestDTO dto) {
        Tarea tarea = tareaMapper.toEntity(dto);
        Tarea actualizada = tareaService.actualizar(id, tarea);
        return ResponseEntity.ok(tareaMapper.toDTO(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tareaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/completar")
    public ResponseEntity<TareaResponseDTO> completar(@PathVariable Long id) {
        Tarea tarea = tareaService.marcarCompletada(id);
        return ResponseEntity.ok(tareaMapper.toDTO(tarea));
    }

    @GetMapping("/resumen/{creador}")
    public ResponseEntity<ResumenCreadorDTO> resumen(@PathVariable String creador) {
        ResumenCreadorDTO resumen = tareaService.resumenPorCreador(creador);
        return ResponseEntity.ok(resumen);
    }
}

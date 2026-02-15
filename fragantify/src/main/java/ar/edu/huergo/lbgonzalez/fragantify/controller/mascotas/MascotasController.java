package ar.edu.huergo.lbgonzalez.fragantify.controller.mascotas;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.lbgonzalez.fragantify.dto.mascotas.MascotaRequestDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.mascotas.MascotaResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.service.mascotas.MascotasService;

@RestController
@RequestMapping("/api/mascotas")
public class MascotasController {

    private final MascotasService mascotaService;

    public MascotasController(MascotasService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @PostMapping
    public ResponseEntity<MascotaResponseDTO> crear(@RequestBody MascotaRequestDTO requestDto) {
        MascotaResponseDTO creada = mascotaService.crearMascota(requestDto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponseDTO>> listar() {
        List<MascotaResponseDTO> mascotas = mascotaService.listarMascotas();
        return ResponseEntity.ok(mascotas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> obtenerPorId(@PathVariable Long id) {
        MascotaResponseDTO dto = mascotaService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponseDTO> actualizar(@PathVariable Long id,
                                                         @RequestBody MascotaRequestDTO requestDto) {
        MascotaResponseDTO actualizada = mascotaService.actualizarMascota(id, requestDto);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminarMascota(id);
        return ResponseEntity.noContent().build();
    }
}


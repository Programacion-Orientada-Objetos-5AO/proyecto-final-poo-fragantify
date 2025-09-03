package com.example.demo.controller;

import com.example.demo.dto.PerfumeDto;
import com.example.demo.entity.Perfume;
import com.example.demo.service.PerfumeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfumes")
public class PerfumeController {

    @Autowired
    private PerfumeService perfumeService;

    // GET todos los perfumes
    @GetMapping // http://localhost:8080/empleado
    public List<PerfumeDto> getPerfumes() {
        return perfumeService.getPerfumes();
    }

    // GET perfume por ID
    @GetMapping("/{id}")
    public ResponseEntity<Perfume> getPerfume(@PathVariable Long id) {
        return perfumeService.getPerfume(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST crear perfume
    @PostMapping
    public ResponseEntity<String> crearPerfume(@Valid @RequestBody PerfumeDto perfumeDto) {
        perfumeService.crearPerfume(perfumeDto);
        return ResponseEntity.ok("Perfume creado correctamente");
    }

    // PUT actualizar perfume
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPerfume(@PathVariable Long id,
                                                    @Valid @RequestBody PerfumeDto perfumeDto) {
        perfumeService.actualizarPerfume(id, perfumeDto);
        return ResponseEntity.ok("Perfume actualizado correctamente");
    }

    // DELETE eliminar perfume
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPerfume(@PathVariable Long id) {
        perfumeService.eliminarPerfume(id);
        return ResponseEntity.ok("Perfume eliminado correctamente");
    }
}

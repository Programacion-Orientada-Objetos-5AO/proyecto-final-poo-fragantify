package ar.edu.huergo.lbgonzalez.fragantify.controller;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Marca;
import ar.edu.huergo.lbgonzalez.fragantify.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public List<Marca> obtenerTodas() {
        return marcaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Marca> crearMarca(@RequestBody Marca marca) {
        Marca nuevaMarca = marcaRepository.save(marca);
        return ResponseEntity.ok(nuevaMarca);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> obtenerPorId(@PathVariable Long id) {
        return marcaRepository.findById(id)
                .map(marca -> ResponseEntity.ok(marca))
                .orElse(ResponseEntity.notFound().build());
    }
}

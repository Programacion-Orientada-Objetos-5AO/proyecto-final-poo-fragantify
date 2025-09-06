
import ar.edu.huergo.lbgonzalez.fragantify.dto.PerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.service.PerfumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/perfume")
public class PerfumeController {

    @Autowired
    private PerfumeService perfumeService;

    @GetMapping
    public List<PerfumeDTO> getPerfumes() {
        return this.perfumeService.getPerfumes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfumeDTO> getPerfume(@PathVariable Long id) {
        Optional<Perfume> perfumeOpt = this.perfumeService.getPerfume(id);
        if (perfumeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Perfume perfume = perfumeOpt.get();
        return ResponseEntity.ok(new PerfumeDTO(perfume.getId(), perfume.getNombre()));
    }

    @PostMapping
    public ResponseEntity<String> crearPerfume(@RequestBody PerfumeDTO perfumeDto) {
        try {
            this.perfumeService.crearPerfume(perfumeDto);
            return ResponseEntity.created(null).body("Perfume creado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarPerfume(@PathVariable Long id, @RequestBody PerfumeDTO perfumeDto) {
        try {
            this.perfumeService.actualizarPerfume(id, perfumeDto);
            return ResponseEntity.ok("Perfume actualizado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPerfume(@PathVariable Long id) {
        try {
            this.perfumeService.eliminarPerfume(id);
            return ResponseEntity.ok("Perfume eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

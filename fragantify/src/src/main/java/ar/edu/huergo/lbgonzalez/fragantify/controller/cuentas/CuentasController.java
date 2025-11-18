package ar.edu.huergo.lbgonzalez.fragantify.controller.cuentas;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.lbgonzalez.fragantify.dto.cuentas.CrearActualizarCuentaDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.cuentas.MostrarCuentaDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.cuentas.Cuentas;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.cuentas.CuentasMapper;
import ar.edu.huergo.lbgonzalez.fragantify.service.usuario.CuentasService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cuentas")
public class CuentasController {

    @Autowired
    private CuentasService cuentasService;

    @Autowired
    private CuentasMapper cuentasMapper;

    // GET /api/cuentas
    @GetMapping
    public ResponseEntity<List<MostrarCuentaDTO>> obtenerTodasLasCuentas() {
        List<Cuentas> cuentas = cuentasService.getCuentas();
        List<MostrarCuentaDTO> cuentasDto = cuentasMapper.toDTOList(cuentas);
        return ResponseEntity.ok(cuentasDto);
    }

    // GET /api/cuentas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MostrarCuentaDTO> obtenerCuentaPorId(@PathVariable Long id) {
        Cuentas cuenta = cuentasService.getCuentas(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id " + id));
        return ResponseEntity.ok(cuentasMapper.toDTO(cuenta));
    }

    // POST /api/cuentas
    @PostMapping
    public ResponseEntity<MostrarCuentaDTO> crearCuenta(@Valid @RequestBody CrearActualizarCuentaDTO dto) {
        Cuentas cuenta = cuentasMapper.toEntity(dto);
        Cuentas cuentaCreada = cuentasService.crearCuentas(cuenta);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cuentaCreada.getId())
                .toUri();
        return ResponseEntity.created(location).body(cuentasMapper.toDTO(cuentaCreada));
    }

    // PUT /api/cuentas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<MostrarCuentaDTO> actualizarCuenta(@PathVariable Long id,
                                                             @Valid @RequestBody CrearActualizarCuentaDTO dto) {
        Cuentas cuenta = cuentasMapper.toEntity(dto);
        Cuentas cuentaActualizada = cuentasService.actualizarCuentas(id, cuenta);
        return ResponseEntity.ok(cuentasMapper.toDTO(cuentaActualizada));
    }

    // DELETE /api/cuentas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentasService.eliminarCuentas(id); // si tu service se llama así, lo dejo igual
        return ResponseEntity.noContent().build();
    }
}

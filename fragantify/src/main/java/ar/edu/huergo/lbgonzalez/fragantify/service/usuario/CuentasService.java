package ar.edu.huergo.lbgonzalez.fragantify.service.usuario;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.cuentas.Cuentas;
import ar.edu.huergo.lbgonzalez.fragantify.repository.usuarios.CuentasRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@Service
public class CuentasService {

    @Autowired
    private CuentasRepository usuarioRepository;

    @Autowired
    private Validator validator;

     public List<Cuentas> getCuentas() {
        return usuarioRepository.findAll();
    }

    public Optional<Cuentas> getCuentas(Long id) {
        return usuarioRepository.findById(id);
    }

    public Cuentas crearCuentas(@Valid Cuentas usuarios) {
        validateUsuarios(usuarios);
        return usuarioRepository.save(usuarios);
    }

    public Cuentas actualizarCuentas(Long id, @Valid Cuentas usuarios) {
        validateUsuarios(usuarios);
        Cuentas existing = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuarios no encontrado con id " + id));
        existing.setNombre(usuarios.getNombre());
        existing.setMail(usuarios.getMail());
        existing.setContraseña(usuarios.getContraseña());
        existing.setEdad(usuarios.getEdad());
        existing.setTipo(usuarios.getTipo());
        return usuarioRepository.save(existing);
    }

    public void eliminarCuentas(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuarios no encontrado con id " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private void validateUsuarios(Cuentas usuarios) {
        Set<ConstraintViolation<Cuentas>> violations = validator.validate(usuarios);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Cuentas> violation : violations) {
                sb.append(violation.getPropertyPath())
                  .append(": ")
                  .append(violation.getMessage())
                  .append("; ");
            }
            throw new IllegalArgumentException("Errores de validación: " + sb.toString());
        }
    }
}

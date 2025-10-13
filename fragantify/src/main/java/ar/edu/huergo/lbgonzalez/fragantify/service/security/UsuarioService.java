package ar.edu.huergo.lbgonzalez.fragantify.service.security;

import java.util.List;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.security.Rol;
import ar.edu.huergo.lbgonzalez.fragantify.entity.security.Usuario;
import ar.edu.huergo.lbgonzalez.fragantify.repository.security.RolRepository;
import ar.edu.huergo.lbgonzalez.fragantify.repository.security.UsuarioRepository;
import ar.edu.huergo.lbgonzalez.fragantify.util.PasswordValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario registrar(Usuario usuario, String password, String verificacionPassword) {
        if (password == null || verificacionPassword == null) {
            throw new IllegalArgumentException("Las contrasenas no pueden ser null");
        }
        if (!password.equals(verificacionPassword)) {
            throw new IllegalArgumentException("Las contrasenas no coinciden");
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya esta en uso");
        }

        PasswordValidator.validate(password);
        usuario.setPassword(passwordEncoder.encode(password));
        Rol rolCliente = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new IllegalArgumentException("Rol 'CLIENTE' no encontrado"));
        usuario.setRoles(Set.of(rolCliente));
        return usuarioRepository.save(usuario);
    }
}

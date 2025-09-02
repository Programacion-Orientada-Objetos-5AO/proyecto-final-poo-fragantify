package ar.edu.huergo.lbgonzalez.fragantify.repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
}

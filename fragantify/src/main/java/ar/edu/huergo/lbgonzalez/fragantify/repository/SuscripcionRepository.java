package ar.edu.huergo.lbgonzalez.fragantify.repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Suscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SuscripcionRepository extends JpaRepository<Suscripcion, Long> {
    List<Suscripcion> findByUsuarioId(Long usuarioId);
}

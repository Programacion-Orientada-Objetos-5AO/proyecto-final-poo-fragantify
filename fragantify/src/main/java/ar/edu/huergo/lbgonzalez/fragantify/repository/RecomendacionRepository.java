package ar.edu.huergo.lbgonzalez.fragantify.repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Recomendacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {
    List<Recomendacion> findByUsuarioId(Long usuarioId);
}

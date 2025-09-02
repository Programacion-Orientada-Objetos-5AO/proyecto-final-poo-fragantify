package ar.edu.huergo.lbgonzalez.fragantify.repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Reseña;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReseñaRepository extends JpaRepository<Reseña, Long> {
    List<Reseña> findByPerfumeId(Long perfumeId);
    List<Reseña> findByUsuarioId(Long usuarioId);
}

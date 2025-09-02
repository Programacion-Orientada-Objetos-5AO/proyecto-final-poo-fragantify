package ar.edu.huergo.lbgonzalez.fragantify.repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuarioId(Long usuarioId);
    Optional<Favorito> findByUsuarioIdAndPerfumeId(Long usuarioId, Long perfumeId);
}

package ar.edu.huergo.lbgonzalez.fragantify.repository.marca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.marca.Marca;

@Repository
public interface MarcasRepository  extends JpaRepository<Marca, Long> {
}

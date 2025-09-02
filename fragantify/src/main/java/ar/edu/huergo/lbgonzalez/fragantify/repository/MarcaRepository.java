package ar.edu.huergo.lbgonzalez.fragantify.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
}

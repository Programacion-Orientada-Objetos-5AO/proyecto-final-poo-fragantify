package ar.edu.huergo.lbgonzalez.fragantify.repository.perfume;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    List<Perfume> findByNombreContainingIgnoreCase(String nombre);

    List<Perfume> findByMarcaContainingIgnoreCase(String marca);

    List<Perfume> findByPrecioBetween(double min, double max);

    List<Perfume> findByFamiliaOlfativaContainingIgnoreCase(String familiaOlfativa);
}

package ar.edu.huergo.lbgonzalez.fragantify.repository.perfume;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    // Busca perfumes cuyo nombre contenga cierto texto, sin importar mayúsculas
    List<Perfume> findByNombreContainingIgnoreCase(String nombre);

    // Busca perfumes cuya marca contenga cierto texto, sin importar mayúsculas
    List<Perfume> findByMarcaContainingIgnoreCase(String marca);

    // 🔥 ESTE es el que falta para tu test
    List<Perfume> findByPrecioBetween(double min, double max);

    // Busca perfumes cuya familia olfativa contenga cierto texto, sin importar mayúsculas
    List<Perfume> findByFamiliaOlfativaContainingIgnoreCase(String familiaOlfativa);
}

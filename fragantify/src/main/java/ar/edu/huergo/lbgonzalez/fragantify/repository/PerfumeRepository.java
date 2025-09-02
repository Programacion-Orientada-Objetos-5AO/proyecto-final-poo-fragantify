package ar.edu.huergo.lbgonzalez.fragantify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;

public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    List<Perfume> findByMarcaNombre(String marcaNombre);

    @Query("SELECT p FROM Perfume p WHERE (:familiaOlfativa IS NULL OR p.familiaOlfativa = :familiaOlfativa) AND (:precioMin IS NULL OR p.precio >= :precioMin) AND (:precioMax IS NULL OR p.precio <= :precioMax)")
    List<Perfume> findByFilters(@Param("familiaOlfativa") String familiaOlfativa, @Param("precioMin") Double precioMin, @Param("precioMax") Double precioMax);
}

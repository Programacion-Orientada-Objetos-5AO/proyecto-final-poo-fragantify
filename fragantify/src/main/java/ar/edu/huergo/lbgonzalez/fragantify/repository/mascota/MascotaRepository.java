package ar.edu.huergo.lbgonzalez.fragantify.repository.mascota;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.mascota.MascotaEntity;

public interface MascotaRepository extends JpaRepository<MascotaEntity,Long> {
}

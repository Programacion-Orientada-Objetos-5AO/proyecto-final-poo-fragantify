package ar.edu.huergo.lbgonzalez.fragantify.repository.perfume;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.ExternalFragrance;

public interface ExternalFragranceRepository extends JpaRepository<ExternalFragrance, Long> {

    Optional<ExternalFragrance> findByExternalId(String externalId);
}

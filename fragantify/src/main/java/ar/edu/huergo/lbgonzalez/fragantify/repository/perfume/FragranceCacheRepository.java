package ar.edu.huergo.lbgonzalez.fragantify.repository.perfume;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.cache.FragranceCacheEntry;

public interface FragranceCacheRepository extends JpaRepository<FragranceCacheEntry, Long> {

    Optional<FragranceCacheEntry> findTopByOrderByCreatedAtDesc();
}

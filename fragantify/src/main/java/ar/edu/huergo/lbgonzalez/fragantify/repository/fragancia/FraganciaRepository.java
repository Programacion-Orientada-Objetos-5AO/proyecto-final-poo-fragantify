package ar.edu.huergo.lbgonzalez.fragantify.repository.fragancia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.fragance.Fragance;

@Repository
public interface FraganciaRepository extends JpaRepository<Fragance,Long> {
    
}

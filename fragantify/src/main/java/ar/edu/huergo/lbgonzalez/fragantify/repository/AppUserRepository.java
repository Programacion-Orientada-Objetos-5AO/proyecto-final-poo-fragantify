package ar.edu.huergo.lbgonzalez.fragantify.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    // Buscar por email (para login y validaciones)
    Optional<AppUser> findByEmail(String email);

    // Verificar existencia de email (para registro)
    boolean existsByEmail(String email);
}

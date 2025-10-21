package ar.edu.huergo.lbgonzalez.fragantify.config;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.lbgonzalez.fragantify.entity.security.Rol;
import ar.edu.huergo.lbgonzalez.fragantify.repository.security.RolRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);

    private final RolRepository rolRepository;

    @Override
    @Transactional
    public void run(String... args) {
        Rol admin = ensureRole("ADMIN");
        Rol cliente = ensureRole("CLIENTE");
        LOGGER.debug("Roles inicializados: {}", Set.of(admin.getNombre(), cliente.getNombre()));
    }

    private Rol ensureRole(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseGet(() -> rolRepository.save(new Rol(nombre)));
    }
}

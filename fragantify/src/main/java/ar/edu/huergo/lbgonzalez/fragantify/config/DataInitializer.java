package main.java.ar.edu.huergo.lbgonzalez.fragantify.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.lbgonzalez.fragantify.entity.AppUser;
import ar.edu.huergo.lbgonzalez.fragantify.entity.Rol;
import ar.edu.huergo.lbgonzalez.fragantify.repository.AppUserRepository;
import ar.edu.huergo.lbgonzalez.fragantify.repository.RolRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(RolRepository rolRepository,
                               AppUserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {

            // Crear roles si no existen
            Rol adminRole = rolRepository.findByNombre("ADMIN")
                    .orElseGet(() -> rolRepository.save(new Rol("ADMIN")));
            Rol userRole = rolRepository.findByNombre("USER")
                    .orElseGet(() -> rolRepository.save(new Rol("USER")));

            // Crear usuario admin si no existe
            if (userRepository.findByUsername("admin").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // contraseña: admin123
                admin.setRoles(Set.of(adminRole, userRole));
                userRepository.save(admin);
                System.out.println("✅ Usuario admin creado: username=admin, password=admin123");
            }
        };
    }
}

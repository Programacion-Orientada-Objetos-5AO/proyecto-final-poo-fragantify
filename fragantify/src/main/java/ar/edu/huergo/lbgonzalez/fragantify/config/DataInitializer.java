package ar.edu.huergo.lbgonzalez.fragantify.config;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.entity.security.Rol;
import ar.edu.huergo.lbgonzalez.fragantify.entity.security.Usuario;
import ar.edu.huergo.lbgonzalez.fragantify.repository.perfume.PerfumeRepository;
import ar.edu.huergo.lbgonzalez.fragantify.repository.security.RolRepository;
import ar.edu.huergo.lbgonzalez.fragantify.repository.security.UsuarioRepository;
import ar.edu.huergo.lbgonzalez.fragantify.util.PasswordValidator;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            RolRepository rolRepository,
            UsuarioRepository usuarioRepository,
            PasswordEncoder encoder,
            PerfumeRepository perfumeRepository) {

        return args -> {
            Rol admin = rolRepository.findByNombre("ADMIN")
                    .orElseGet(() -> rolRepository.save(new Rol("ADMIN")));
            Rol cliente = rolRepository.findByNombre("CLIENTE")
                    .orElseGet(() -> rolRepository.save(new Rol("CLIENTE")));

            final String adminUsername = "admin@fragantify.app";
            if (usuarioRepository.findByUsername(adminUsername).isEmpty()) {
                String adminPassword = "AdminSuperSegura@123";
                PasswordValidator.validate(adminPassword);
                Usuario u = new Usuario(adminUsername, encoder.encode(adminPassword));
                u.setRoles(Set.of(admin));
                usuarioRepository.save(u);
            }

            final String clienteUsername = "cliente@fragantify.app";
            if (usuarioRepository.findByUsername(clienteUsername).isEmpty()) {
                String clientePassword = "ClienteSuperSegura@123";
                PasswordValidator.validate(clientePassword);
                Usuario u = new Usuario(clienteUsername, encoder.encode(clientePassword));
                u.setRoles(Set.of(cliente));
                usuarioRepository.save(u);
            }

            if (perfumeRepository.count() == 0) {
                perfumeRepository.saveAll(List.of(
                    new Perfume("Aroma Supremo", "Fragantify", 155.50, "Amaderada"),
                    new Perfume("Brisa Marina", "OceanScents", 98.00, "Acuatica"),
                    new Perfume("Noche Ambar", "Luna", 210.00, "Oriental"),
                    new Perfume("Verano Citrico", "CitrusLab", 120.00, "Citrica"),
                    new Perfume("Flor de Loto", "Botanica", 180.00, "Floral" )
                ));
            }
        };
    }
}

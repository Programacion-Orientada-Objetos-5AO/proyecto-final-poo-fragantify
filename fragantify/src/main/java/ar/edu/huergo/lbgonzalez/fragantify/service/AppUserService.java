package ar.edu.huergo.lbgonzalez.fragantify.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.AppUser;
import ar.edu.huergo.lbgonzalez.fragantify.repository.AppUserRepository;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<AppUser> getAllUsuarios() {
        return appUserRepository.findAll();
    }

    public AppUser registrar(AppUser user, String password, String verificacionPassword) {
        if (password == null || verificacionPassword == null) {
            throw new IllegalArgumentException("Las contraseñas no pueden ser null");
        }
        if (!password.equals(verificacionPassword)) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        if (appUserRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El email ya está en uso");
        }
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(AppUser.Role.USER);
        return appUserRepository.save(user);
    }
}

package main.java.ar.edu.huergo.lbgonzalez.fragantify.controller.perfume;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import ar.edu.huergo.lbgonzalez.fragantify.dto.LoginRequest;
import ar.edu.huergo.lbgonzalez.fragantify.dto.AuthResponse;
import ar.edu.huergo.lbgonzalez.fragantify.dto.RegisterRequest;
import ar.edu.huergo.lbgonzalez.fragantify.config.JwtTokenService;
import ar.edu.huergo.lbgonzalez.fragantify.entity.AppUser;
import ar.edu.huergo.lbgonzalez.fragantify.entity.Rol;
import ar.edu.huergo.lbgonzalez.fragantify.repository.AppUserRepository;
import ar.edu.huergo.lbgonzalez.fragantify.repository.RolRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AppUserRepository appUserRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenService jwtTokenService,
                          AppUserRepository appUserRepository,
                          RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.appUserRepository = appUserRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // === LOGIN ===
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = jwtTokenService.generateToken(authentication.getName());
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(401).build();
        }
    }

    // === REGISTER ===
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        if (appUserRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        // Rol por defecto: USER
        Rol userRole = rolRepository.findByNombre("USER")
                .orElseGet(() -> rolRepository.save(new Rol("USER")));

        AppUser newUser = new AppUser();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(Set.of(userRole));

        appUserRepository.save(newUser);

        return ResponseEntity.ok("Usuario registrado con éxito");
    }
}

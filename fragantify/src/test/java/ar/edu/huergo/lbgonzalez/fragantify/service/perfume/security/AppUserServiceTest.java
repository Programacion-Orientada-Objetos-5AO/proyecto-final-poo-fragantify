package ar.edu.huergo.lbgonzalez.fragantify.service.perfume.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import ar.edu.huergo.lbgonzalez.fragantify.entity.AppUser;
import ar.edu.huergo.lbgonzalez.fragantify.repository.AppUserRepository;
import ar.edu.huergo.lbgonzalez.fragantify.service.AppUserService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - AppUserService")
class AppUserServiceTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserService appUserService;

    private AppUser userEjemplo;

    @BeforeEach
    void setUp() {
        userEjemplo = new AppUser("usuario@test.com", null, "Usuario");
        userEjemplo.setId(1L);
        userEjemplo.setRole(AppUser.Role.USER);
    }

    @Test
    @DisplayName("Debería obtener todos los usuarios")
    void deberiaObtenerTodosLosUsuarios() {
        // Given
        when(appUserRepository.findAll()).thenReturn(List.of(userEjemplo));

        // When
        List<AppUser> resultado = appUserService.getAllUsuarios();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("usuario@test.com", resultado.get(0).getEmail());
        verify(appUserRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería registrar usuario correctamente")
    void deberiaRegistrarUsuarioCorrectamente() {
        // Given
        String password = "password123";
        String verificacionPassword = "password123";
        String passwordEncriptado = "encrypted_password";

        when(appUserRepository.existsByEmail(userEjemplo.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(passwordEncriptado);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(userEjemplo);

        // When
        AppUser resultado = appUserService.registrar(userEjemplo, password, verificacionPassword);

        // Then
        assertNotNull(resultado);
        verify(appUserRepository, times(1)).existsByEmail(userEjemplo.getEmail());
        verify(passwordEncoder, times(1)).encode(password);
        verify(appUserRepository, times(1)).save(userEjemplo);

        // Verificar que la contraseña fue encriptada y el rol asignado
        assertEquals(passwordEncriptado, userEjemplo.getPasswordHash());
        assertEquals(AppUser.Role.USER, userEjemplo.getRole());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando las contraseñas no coinciden")
    void deberiaLanzarExcepcionCuandoContraseniasNoCoinciden() {
        String password = "password123";
        String verificacionPassword = "password456";

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> appUserService.registrar(userEjemplo, password, verificacionPassword));

        assertEquals("Las contraseñas no coinciden", ex.getMessage());
        verify(appUserRepository, never()).existsByEmail(any());
        verify(passwordEncoder, never()).encode(any());
        verify(appUserRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando el email ya existe")
    void deberiaLanzarExcepcionCuandoEmailYaExiste() {
        String password = "password123";
        String verificacionPassword = "password123";

        when(appUserRepository.existsByEmail(userEjemplo.getEmail())).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> appUserService.registrar(userEjemplo, password, verificacionPassword));

        assertEquals("El email ya está en uso", ex.getMessage());
        verify(appUserRepository, times(1)).existsByEmail(userEjemplo.getEmail());
        verify(passwordEncoder, never()).encode(any());
        verify(appUserRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar excepción cuando las contraseñas son null")
    void deberiaLanzarExcepcionCuandoContraseniasNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> appUserService.registrar(userEjemplo, null, null));
        assertEquals("Las contraseñas no pueden ser null", ex.getMessage());
    }
}

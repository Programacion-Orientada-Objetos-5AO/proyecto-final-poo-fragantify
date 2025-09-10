package ar.edu.huergo.lbgonzalez.fragantify.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@DisplayName("Tests de Seguridad - JwtTokenService")
class JwtTokenServiceTest {

    private JwtTokenService jwtTokenService;
    private UserDetails userDetails;

    private static final String SECRET_KEY =
            "mi-clave-secreta-para-jwt-que-debe-ser-lo-suficientemente-larga-para-ser-segura";
    private static final long EXPIRATION_MS = 3600000; // 1 hora

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @BeforeEach
    void setUp() {
        // Servicio con valores de prueba
        jwtTokenService = new JwtTokenService(SECRET_KEY, EXPIRATION_MS);

        // Mock de UserDetails
        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("usuario@test.com");
        when(userDetails.getAuthorities())
            .thenReturn((Collection) Arrays.asList(
                new SimpleGrantedAuthority("ROLE_CLIENTE"),
                new SimpleGrantedAuthority("ROLE_USER")
            ));
    }

    @Test
    @DisplayName("Debería generar token JWT válido")
    void deberiaGenerarTokenJwtValido() {
        List<String> roles = Arrays.asList("ROLE_CLIENTE", "ROLE_USER");
        String token = jwtTokenService.generarToken(userDetails, roles);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains("."), "El token JWT debería tener formato con puntos");

        String[] partes = token.split("\\.");
        assertEquals(3, partes.length, "El token JWT debería tener 3 partes");
    }

    @Test
    @DisplayName("Debería extraer username del token correctamente")
    void deberiaExtraerUsernameDelToken() {
        String token = jwtTokenService.generarToken(userDetails, List.of("ROLE_CLIENTE"));
        String usernameExtraido = jwtTokenService.extraerUsername(token);
        assertEquals("usuario@test.com", usernameExtraido);
    }

    @Test
    @DisplayName("Debería validar token correctamente para usuario válido")
    void deberiaValidarTokenCorrectamente() {
        String token = jwtTokenService.generarToken(userDetails, List.of("ROLE_CLIENTE"));
        assertTrue(jwtTokenService.esTokenValido(token, userDetails));
    }

    @Test
    @DisplayName("Debería rechazar token para usuario diferente")
    void deberiaRechazarTokenParaUsuarioDiferente() {
        String token = jwtTokenService.generarToken(userDetails, List.of("ROLE_CLIENTE"));

        UserDetails otroUsuario = mock(UserDetails.class);
        when(otroUsuario.getUsername()).thenReturn("otro@test.com");

        assertFalse(jwtTokenService.esTokenValido(token, otroUsuario));
    }

    @Test
    @DisplayName("Debería rechazar token malformado")
    void deberiaRechazarTokenMalformado() {
        String tokenMalformado = "token.malformado.invalido";
        assertThrows(Exception.class, () -> jwtTokenService.extraerUsername(tokenMalformado));
    }

    @Test
    @DisplayName("Debería rechazar token vacío")
    void deberiaRechazarTokenVacio() {
        assertThrows(Exception.class, () -> jwtTokenService.extraerUsername(""));
    }

    @Test
    @DisplayName("Debería rechazar token null")
    void deberiaRechazarTokenNull() {
        assertThrows(Exception.class, () -> jwtTokenService.extraerUsername(null));
    }

    @Test
    @DisplayName("Debería generar tokens diferentes para llamadas consecutivas")
    void deberiaGenerarTokensDiferentesParaLlamadasConsecutivas() throws InterruptedException {
        String token1 = jwtTokenService.generarToken(userDetails, List.of("ROLE_CLIENTE"));
        Thread.sleep(1000);
        String token2 = jwtTokenService.generarToken(userDetails, List.of("ROLE_CLIENTE"));
        assertNotEquals(token1, token2, "Los tokens deberían ser diferentes");
    }

    @Test
    @DisplayName("Debería manejar roles vacíos")
    void deberiaManejarRolesVacios() {
        String token = jwtTokenService.generarToken(userDetails, List.of());
        assertNotNull(token);
        assertFalse(token.isEmpty());

        String usernameExtraido = jwtTokenService.extraerUsername(token);
        assertEquals("usuario@test.com", usernameExtraido);
    }

    @Test
    @DisplayName("Debería manejar múltiples roles")
    void deberiaManejarMultiplesRoles() {
        List<String> multiplesRoles = Arrays.asList("ROLE_CLIENTE", "ROLE_ADMIN", "ROLE_MODERADOR");
        String token = jwtTokenService.generarToken(userDetails, multiplesRoles);
        assertNotNull(token);
        assertFalse(token.isEmpty());

        String usernameExtraido = jwtTokenService.extraerUsername(token);
        assertEquals("usuario@test.com", usernameExtraido);
    }

    @Test
    @DisplayName("Debería rechazar token expirado")
    void deberiaRechazarTokenExpirado() throws InterruptedException {
        JwtTokenService corto = new JwtTokenService(SECRET_KEY, 1);
        String token = corto.generarToken(userDetails, List.of("ROLE_CLIENTE"));
        Thread.sleep(10);
        assertThrows(Exception.class, () -> corto.extraerUsername(token));
    }

    @Test
    @DisplayName("Debería validar token dentro del tiempo de expiración")
    void deberiaValidarTokenDentroDelTiempoDeExpiracion() {
        String token = jwtTokenService.generarToken(userDetails, List.of("ROLE_CLIENTE"));
        assertTrue(jwtTokenService.esTokenValido(token, userDetails));
    }

    @Test
    @DisplayName("Debería manejar usernames con caracteres especiales")
    void deberiaManejarUsernamesConCaracteresEspeciales() {
        UserDetails usuarioEspecial = mock(UserDetails.class);
        when(usuarioEspecial.getUsername()).thenReturn("usuario.especial+test@dominio-test.com");

        String token = jwtTokenService.generarToken(usuarioEspecial, List.of("ROLE_CLIENTE"));
        String usernameExtraido = jwtTokenService.extraerUsername(token);

        assertEquals("usuario.especial+test@dominio-test.com", usernameExtraido);
        assertTrue(jwtTokenService.esTokenValido(token, usuarioEspecial));
    }
}
    
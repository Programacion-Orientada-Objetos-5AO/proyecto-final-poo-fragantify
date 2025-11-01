package ar.edu.huergo.lbgonzalez.fragantify.dto.security;

/**
 * Standard auth response containing the issued token and basic user info.
 */
public record AuthResponseDTO(String token, UsuarioDTO user) {
}

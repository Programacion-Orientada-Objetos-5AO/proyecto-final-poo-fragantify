package ar.edu.huergo.lbgonzalez.fragantify.dto.security;

import java.util.List;

public record UsuarioDTO(String username, List<String> roles) {
    
}

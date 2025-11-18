package ar.edu.huergo.lbgonzalez.fragantify.controller.usuario;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.lbgonzalez.fragantify.dto.security.AuthResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.security.LoginDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.security.RegistrarDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.security.UsuarioDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.security.Usuario;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.security.UsuarioMapper;
import ar.edu.huergo.lbgonzalez.fragantify.service.security.JwtTokenService;
import ar.edu.huergo.lbgonzalez.fragantify.service.security.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        Usuario usuario = usuarioService.obtenerPorUsername(request.username());
        List<String> roles = usuario.getRoles().stream()
                .map(rol -> rol.getNombre())
                .toList();
        String token = jwtTokenService.generarToken(userDetails, roles);
        UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuario);

        return ResponseEntity.ok(new AuthResponseDTO(token, usuarioDTO));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegistrarDTO request) {
        Usuario usuario = usuarioService.registrar(
                usuarioMapper.toEntity(request),
                request.password(),
                request.verificacionPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getUsername());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList();
        String token = jwtTokenService.generarToken(userDetails, roles);
        UsuarioDTO usuarioDTO = usuarioMapper.toDTO(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponseDTO(token, usuarioDTO));
    }
}

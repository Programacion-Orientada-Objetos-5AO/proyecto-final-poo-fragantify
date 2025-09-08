package ar.edu.huergo.lbgonzalez.fragantify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desactiva CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/**").permitAll() // todas las rutas abiertas
            )
            .formLogin(form -> form.disable()) // desactiva formulario de login
            .httpBasic(basic -> basic.disable()); // desactiva Basic Auth
        return http.build();
    }
}

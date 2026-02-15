package ar.edu.huergo.lbgonzalez.fragantify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.huergo.lbgonzalez.fragantify.repository.security.UsuarioRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /* =======================
       1) API: JWT, stateless
       ======================= */
    @Bean
    @Order(1)
    SecurityFilterChain apiSecurity(HttpSecurity http,
                                    JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
            .securityMatcher("/api/**")
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // --- AUTH ---
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()

                // --- PERFUMES ---
                .requestMatchers(HttpMethod.GET,    "/api/perfumes/**").permitAll()           // catálogo público
                .requestMatchers(HttpMethod.POST,   "/api/perfumes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/perfumes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/perfumes/**").hasRole("ADMIN")

                // --- CUENTAS (datos sensibles) ---
                .requestMatchers(HttpMethod.GET,    "/api/cuentas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,   "/api/cuentas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/cuentas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/cuentas/**").hasRole("ADMIN")

                // --- MARCAS ---
                .requestMatchers(HttpMethod.GET,    "/api/marcas/**").permitAll()
                .requestMatchers(HttpMethod.POST,   "/api/marcas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/marcas/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/marcas/**").hasRole("ADMIN")

                // --- RECOMENDACIONES ---
                .requestMatchers(HttpMethod.GET,    "/api/recomendaciones/**").permitAll()
                .requestMatchers(HttpMethod.POST,   "/api/recomendaciones/**").hasAnyRole("CLIENTE", "ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/recomendaciones/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/recomendaciones/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET,    "/api/fragancias/**").permitAll()
                .requestMatchers(HttpMethod.POST,   "/api/fragancias/**").hasAnyRole("CLIENTE", "ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/fragancias/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/fragancias/**").hasRole("ADMIN")



                // --- MASCOTAS ---
                .requestMatchers(HttpMethod.GET,    "/api/mascotas/**").permitAll()
                .requestMatchers(HttpMethod.POST,   "/api/mascotas/**").permitAll()
                .requestMatchers(HttpMethod.PUT,    "/api/mascotas/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/mascotas/**").permitAll()

                .requestMatchers("/api/tareas/**").permitAll()

                // cualquier otro /api/** requiere JWT válido
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedHandler(accessDeniedHandler())
                .authenticationEntryPoint(authenticationEntryPoint())
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /* =====================================
       2) Web (Thymeleaf): formLogin, stateful
       ===================================== */
        @Bean
        @Order(2)
        SecurityFilterChain webSecurity(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                // ⬇️ agrega estas dos líneas
                .requestMatchers("/login.html", "/app.html").permitAll()
                // estáticos habituales
                .requestMatchers("/", "/login", "/favicon.ico",
                                "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
                )
                .logout(lo -> lo
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
                )
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));

        return http.build();
        }


    /* ===== infra común ===== */

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            response.setStatus(403);
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(
                java.util.Map.of(
                    "type", "https://http.dev/problems/access-denied",
                    "title", "Acceso denegado",
                    "status", 403,
                    "detail", "No tienes permisos para acceder a este recurso"
                )
            );
            response.getWriter().write(body);
        };
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> {
            response.setStatus(401);
            response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
            ObjectMapper mapper = new ObjectMapper();
            String body = mapper.writeValueAsString(
                java.util.Map.of(
                    "type", "https://http.dev/problems/unauthorized",
                    "title", "No autorizado",
                    "status", 401,
                    "detail", "Credenciales inválidas o faltantes"
                )
            );
            response.getWriter().write(body);
        };
    }

    @Bean
    UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return username -> usuarioRepository.findByUsername(username)
            .map(usuario -> org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .roles(usuario.getRoles().stream().map(r -> r.getNombre()).toArray(String[]::new))
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService uds,
                                                        PasswordEncoder encoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }

}







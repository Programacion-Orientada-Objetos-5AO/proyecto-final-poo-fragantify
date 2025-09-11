package test.java.ar.edu.huergo.lbgonzalez.fragantify.controller;

import ar.edu.huergo.lbgonzalez.fragantify.dto.LoginRequest;
import ar.edu.huergo.lbgonzalez.fragantify.dto.RegisterRequest;
import ar.edu.huergo.lbgonzalez.fragantify.repository.AppUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppUserRepository appUserRepository;

    @BeforeEach
    void setup() {
        // Limpia la tabla de usuarios antes de cada test
        appUserRepository.deleteAll();
    }

    @Test
    void testRegisterUser() throws Exception {
        RegisterRequest request = new RegisterRequest("nuevoUser", "password123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuario registrado con éxito"));
    }

    @Test
    void testLoginAfterRegister() throws Exception {
        // Primero registramos el usuario
        RegisterRequest register = new RegisterRequest("juan", "password123");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(register)))
                .andExpect(status().isOk());

        // Luego hacemos login con las mismas credenciales
        LoginRequest login = new LoginRequest("juan", "password123");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()))
                .andExpect(jsonPath("$.type").value("Bearer"));
    }
}

package main.java.ar.edu.huergo.lbgonzalez.fragantify.security;

/**
 * DTO que devuelve el token JWT al cliente.
 */
public class AuthResponse {

    private String token;
    private String type = "Bearer";

    public AuthResponse() {
    }

    public AuthResponse(String token) {
        this.token = token;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

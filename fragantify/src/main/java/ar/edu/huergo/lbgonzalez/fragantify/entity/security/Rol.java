package main.java.ar.edu.huergo.lbgonzalez.fragantify.entity;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * Entidad de rol de seguridad.
 * Mantenerla simple (id + nombre único) permite mapearla luego en AppUser.
 */
@Entity
@Table(name = "roles", uniqueConstraints = {
        @UniqueConstraint(name = "uk_roles_nombre", columnNames = "nombre")
})
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre legible del rol, por ej.: "ADMIN", "USER".
     * Se guarda en mayúsculas y es único.
     */
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    // --- Constructores ---
    public Rol() {
    }

    public Rol(String nombre) {
        this.nombre = nombre != null ? nombre.toUpperCase() : null;
    }

    // --- Getters/Setters ---
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = (nombre != null) ? nombre.toUpperCase() : null;
    }

    // --- equals/hashCode por id para JPA ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rol)) return false;
        Rol rol = (Rol) o;
        return Objects.equals(id, rol.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // --- toString ---
    @Override
    public String toString() {
        return "Rol{id=" + id + ", nombre='" + nombre + "'}";
    }
}

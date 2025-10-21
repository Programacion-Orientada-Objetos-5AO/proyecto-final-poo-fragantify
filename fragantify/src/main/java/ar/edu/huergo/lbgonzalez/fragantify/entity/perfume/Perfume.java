package ar.edu.huergo.lbgonzalez.fragantify.entity.perfume;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="perfume")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Perfume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "La marca es obligatoria")
    @Size(min = 2, max = 100, message = "La marca debe tener entre 2 y 100 caracteres")
    private String marca;

    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;

    @Size(max = 100)
    private String familiaOlfativa;

    private boolean favorito;

    // Constructor para crear perfumes nuevos (sin id)
    public Perfume(String nombre, String marca, double precio) {
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
    }

    // Constructor con familiaOlfativa
    public Perfume(String nombre, String marca, double precio, String familiaOlfativa) {
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.familiaOlfativa = familiaOlfativa;
    }
}

package ar.edu.huergo.lbgonzalez.fragantify.entity.cuentas;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cuentas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cuentas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String nombre;

    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El mail es obligatorio")
    @Size(max = 100)
    private String mail;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 12, max = 100)
    private String contraseña;

    @Positive(message = "La edad es obligatoria y debe ser mínimo de 12 años")
    @Min(value = 12, message = "La edad mínima es 12 años")
    private int edad;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipo;

    public enum TipoCuenta {
        PREMIUM,
        NO_PREMIUM
    }

    // Constructor adicional recibiendo el enum directamente
    public Cuentas(String nombre, String mail, String contraseña, int edad, TipoCuenta tipo) {
        this.nombre = nombre;
        this.mail = mail;
        this.contraseña = contraseña;
        this.edad = edad;
        this.tipo = tipo;
    }


    public Cuentas(String nombre, String mail) {
        this.nombre = nombre;
        this.mail = mail;
        
    }
   
}

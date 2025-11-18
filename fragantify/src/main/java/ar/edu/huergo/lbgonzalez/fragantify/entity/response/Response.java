package ar.edu.huergo.lbgonzalez.fragantify.entity.response;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Response")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El titulo es obligatorio")
    @Size(min = 2, max = 100, message = "El titulo debe tener entre 2 y 100 caracteres")
    private String titulo;


    @NotBlank(message = "La descripcion es obligatorio")
    @Size(min = 2, max = 100, message = "La descripcion debe tener entre 2 y 100 caracteres")
    private String descripcion;

    @NotBlank(message = "El nombre del creador  es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre del creador debe tener entre 2 y 100 caracteres")
    private String creador;

    private Boolean completada;

}

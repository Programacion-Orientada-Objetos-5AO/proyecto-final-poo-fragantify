package ar.edu.huergo.lbgonzalez.fragantify.dto.marcas;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class CrearActualizarMarcaDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    
    @NotBlank(message = "El país de origen es obligatorio")
    @Size(min = 2, max = 100, message = "El país de origen debe tener entre 2 y 100 caracteres")
    private String paisOrigen;
    
    @Positive(message = "El año de fundación debe ser un número positivo")
    @Max(value = 2024, message = "El año de fundación no puede ser mayor al año actual")
    private int añoFundacion;
    
    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;
    
}

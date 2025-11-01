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
public class MarcaDTO {
    //-idMarca
    //-nombre
    //-paisOrigen
    //-añoFundacion
    //-descripcion

    private Long id;
    
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100,message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "El pais de origen es obligatorio")
    @Size(min = 2, max = 100,message = "El pais de origen debe tener entre 2 y 100 caracteres")
    private String paisOrigen;
    
    @Positive(message = "El año de fundacion debe ser mayor a 0")
    @Max(value = 2024, message = "El año de fundacion no puede ser mayor al año actual")
    private int añoFundacion;
    
    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 500,message = "La descripcion no puede tener mas de 500 caracteres")
    private String descripcion;


    
}

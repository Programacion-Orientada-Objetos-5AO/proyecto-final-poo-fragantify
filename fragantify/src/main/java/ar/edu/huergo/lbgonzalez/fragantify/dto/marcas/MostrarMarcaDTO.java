package ar.edu.huergo.lbgonzalez.fragantify.dto.marcas;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)

public class MostrarMarcaDTO extends MarcaDTO {

    public MostrarMarcaDTO(Long id, String nombre, String paisOrigen, int añoFundacion, String descripcion) {
        super(id, nombre, paisOrigen, añoFundacion, descripcion);
    }
    
}

package ar.edu.huergo.lbgonzalez.fragantify.dto.mascotas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MascotaRequestDTO {

    private String nombre;
    private String tipo ;
    private Integer edad ;




    
}

package ar.edu.huergo.lbgonzalez.fragantify.dto.mascotas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MascotaResponseDTO {
    private Long id;
    private String nombre ;
    private String  tipo ;
    private Integer edad ;
    private boolean adoptado ;
}

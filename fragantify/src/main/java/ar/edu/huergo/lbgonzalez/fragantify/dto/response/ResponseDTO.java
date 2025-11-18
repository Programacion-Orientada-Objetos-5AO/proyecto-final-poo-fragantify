package ar.edu.huergo.lbgonzalez.fragantify.dto.response;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    private String creador;
    private Boolean completada;
}





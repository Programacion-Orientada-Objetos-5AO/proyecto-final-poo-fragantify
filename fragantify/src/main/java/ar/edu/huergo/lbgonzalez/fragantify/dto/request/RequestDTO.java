package ar.edu.huergo.lbgonzalez.fragantify.dto.request;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDTO {
    private String titulo;
    private String descripcion;
    private String creador;
}





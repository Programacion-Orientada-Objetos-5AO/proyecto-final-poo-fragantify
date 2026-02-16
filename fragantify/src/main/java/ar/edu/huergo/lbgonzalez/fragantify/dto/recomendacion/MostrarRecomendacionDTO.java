package ar.edu.huergo.lbgonzalez.fragantify.dto.recomendacion;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MostrarRecomendacionDTO extends RecomendacionDTO {

    private String cuentaNombre;
    private String perfumeNombre;

    public MostrarRecomendacionDTO(
        Long id,
        Long cuentaId,
        Long perfumeId,
        int puntuacion,
        String comentario,
        java.time.LocalDate fecha,
        String cuentaNombre,
        String perfumeNombre
    ) {
        super(id, cuentaId, perfumeId, puntuacion, comentario, fecha);
        this.cuentaNombre = cuentaNombre;
        this.perfumeNombre = perfumeNombre;
    }
}

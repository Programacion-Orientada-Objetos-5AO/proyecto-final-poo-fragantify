package ar.edu.huergo.lbgonzalez.fragantify.dto.recomendacion;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CrearActualizarRecomendacionDTO {
    private Long id;

    @NotNull(message = "La cuenta es obligatoria")
    private Long cuentaId;

    @NotNull(message = "El perfume es obligatorio")
    private Long perfumeId;

    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private int puntuacion;

    @NotBlank(message = "El comentario es obligatorio")
    @Size(max = 500, message = "El comentario no puede superar 500 caracteres")
    private String comentario;

    @PastOrPresent(message = "La fecha no puede ser futura")
    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;
}






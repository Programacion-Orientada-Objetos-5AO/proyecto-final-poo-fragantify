package ar.edu.huergo.lbgonzalez.fragantify.dto.perfume;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerfumeExternalDTO {
    private String nombre;
    private String marca;
    private String precio;
    private String imagenUrl;
    private String genero;
}


package ar.edu.huergo.lbgonzalez.fragantify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MostrarPerfumeDTO {
    private Long id;
    private String nombre;
    private String marca;
    private Double precio;
    private String familiaOlfativa;
    private Boolean favorito;
}

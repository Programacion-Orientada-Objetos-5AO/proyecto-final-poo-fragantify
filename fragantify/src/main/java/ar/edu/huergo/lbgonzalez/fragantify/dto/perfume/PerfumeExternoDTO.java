package ar.edu.huergo.lbgonzalez.fragantify.dto.perfume;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un perfume devuelto por la API externa de Fragella.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerfumeExternoDTO {

    /**
     * Identificador provisto por la API externa. Puede ser nulo cuando la API no lo expone.
     */
    private String idExterno;

    private String nombre;
    private String marca;
    private Double precio;
    private String familiaOlfativa;
    private String descripcion;
}

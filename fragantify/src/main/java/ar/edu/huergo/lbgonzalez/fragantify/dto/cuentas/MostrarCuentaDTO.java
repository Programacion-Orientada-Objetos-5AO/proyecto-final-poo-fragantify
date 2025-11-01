package ar.edu.huergo.lbgonzalez.fragantify.dto.cuentas;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MostrarCuentaDTO extends CuentaDTO {

    public MostrarCuentaDTO(Long id, String nombre, String mail) {
        super(id, nombre, mail);
    }
}

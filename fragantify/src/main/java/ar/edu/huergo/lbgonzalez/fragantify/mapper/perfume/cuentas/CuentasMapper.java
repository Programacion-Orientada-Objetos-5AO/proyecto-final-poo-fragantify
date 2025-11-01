package ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.cuentas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.lbgonzalez.fragantify.dto.cuentas.CrearActualizarCuentaDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.cuentas.MostrarCuentaDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.cuentas.Cuentas;

@Component
public class CuentasMapper {

    public MostrarCuentaDTO toDTO(Cuentas cuenta) {
        return new MostrarCuentaDTO(
            cuenta.getId(),
            cuenta.getNombre(),
            cuenta.getMail()
        );
    }

    public Cuentas toEntity(CrearActualizarCuentaDTO dto) {
        return new Cuentas(
            dto.getNombre(),
            dto.getMail(),
            dto.getContraseña(),
            dto.getEdad(),
            dto.getTipo()
        );
    }

    public List<MostrarCuentaDTO> toDTOList(List<Cuentas> cuentas) {
        return cuentas.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}

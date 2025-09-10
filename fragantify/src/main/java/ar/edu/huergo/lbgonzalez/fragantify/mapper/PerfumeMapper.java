package ar.edu.huergo.lbgonzalez.fragantify.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.lbgonzalez.fragantify.dto.CrearActualizarPerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.MostrarPerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;

@Component
public class PerfumeMapper {

    public MostrarPerfumeDTO toDTO(Perfume perfume) {
        return new MostrarPerfumeDTO(
            perfume.getId(),
            perfume.getNombre(),
            perfume.getMarca(),
            perfume.getPrecio(),
            null // categoriaNombre, assuming not implemented yet
        );
    }

    public Perfume toEntity(CrearActualizarPerfumeDTO dto) {
        return new Perfume(
            dto.getNombre(),
            dto.getMarca(),
            dto.getPrecio()
        );
    }

    public List<MostrarPerfumeDTO> toDTOList(List<Perfume> perfumes) {
        return perfumes.stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
}

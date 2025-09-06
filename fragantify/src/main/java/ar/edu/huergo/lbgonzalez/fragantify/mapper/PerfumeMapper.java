package ar.edu.huergo.lbgonzalez.fragantify.mapper;

import ar.edu.huergo.lbgonzalez.fragantify.dto.CrearActualizarPerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.MostrarPerfumeDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;
import java.util.List;
import java.util.stream.Collectors;

public class PerfumeMapper {

    public static MostrarPerfumeDTO toDTO(Perfume perfume) {
        return new MostrarPerfumeDTO(
            perfume.getId(),
            perfume.getNombre(),
            perfume.getMarca(),
            perfume.getPrecio(),
            null // categoriaNombre, assuming not implemented yet
        );
    }

    public static Perfume toEntity(CrearActualizarPerfumeDTO dto) {
        return new Perfume(
            dto.getNombre(),
            dto.getMarca(),
            dto.getPrecio()
        );
    }

    public static List<MostrarPerfumeDTO> toDTOList(List<Perfume> perfumes) {
        return perfumes.stream()
            .map(PerfumeMapper::toDTO)
            .collect(Collectors.toList());
    }
}

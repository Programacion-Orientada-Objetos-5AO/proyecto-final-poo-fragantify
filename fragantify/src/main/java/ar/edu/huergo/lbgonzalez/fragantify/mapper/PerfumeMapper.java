package ar.edu.huergo.lbgonzalez.fragantify.mapper;

import ar.edu.huergo.lbgonzalez.fragantify.dto.PerfumeDto;
import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.entity.Marca;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class PerfumeMapper {

    public static PerfumeDto.MostrarPerfumeDTO toDTO(Perfume perfume) {
        if (perfume == null) return null;
        return new PerfumeDto.MostrarPerfumeDTO(
            perfume.getId(),
            perfume.getNombre(),
            perfume.getTipo(),
            perfume.getFamiliaOlfativa(),
            perfume.getPrecio(),
            perfume.getMarca() != null ? perfume.getMarca().getNombre() : null,
            perfume.getFavorito()
        );
    }

    public static List<PerfumeDto.MostrarPerfumeDTO> toDTOList(List<Perfume> perfumes) {
        return perfumes.stream().map(PerfumeMapper::toDTO).collect(Collectors.toList());
    }

    public static Perfume toEntity(PerfumeDto.CrearActualizarPerfumeDTO dto) {
        if (dto == null) return null;
        Perfume perfume = new Perfume();
        perfume.setNombre(dto.getNombre());
        perfume.setTipo(dto.getTipo());
        perfume.setFamiliaOlfativa(dto.getFamiliaOlfativa());
        perfume.setPrecio(dto.getPrecio());
        // Marca will be set in service
        if (dto.getMarcaId() != null) {
            Marca marca = new Marca();
            marca.setId(dto.getMarcaId());
            perfume.setMarca(marca);
        }
        return perfume;
    }
}

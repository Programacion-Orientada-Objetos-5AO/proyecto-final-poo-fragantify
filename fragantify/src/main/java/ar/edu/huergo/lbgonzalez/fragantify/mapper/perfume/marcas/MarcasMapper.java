package ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.marcas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.lbgonzalez.fragantify.dto.marcas.MostrarMarcaDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.marca.Marca;
import ar.edu.huergo.lbgonzalez.fragantify.dto.marcas.CrearActualizarMarcaDTO;

@Component

public class MarcasMapper {


    public MostrarMarcaDTO toDTO(Marca marca){
        return new MostrarMarcaDTO(
            marca.getId(), 
            marca.getNombre(), 
            marca.getPaisOrigen(),
            marca.getAñoFundacion(), 
            marca.getDescripcion() 
            );
    }


    public Marca toEntity(CrearActualizarMarcaDTO dto){
            return new Marca(
            dto.getNombre(), 
            dto.getPaisOrigen(),
            dto.getAñoFundacion(), 
            dto.getDescripcion() 
            );
    }

    public List<MostrarMarcaDTO> toDTOList(List<Marca>marcas){
        return marcas.stream()
        .map(this::toDTO)
        .collect(Collectors.toList());

    }
    
}

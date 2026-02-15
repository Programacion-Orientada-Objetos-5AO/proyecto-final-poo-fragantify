package ar.edu.huergo.lbgonzalez.fragantify.mapper.mascotas;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.lbgonzalez.fragantify.dto.mascotas.MascotaRequestDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.mascotas.MascotaResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.mascota.MascotaEntity;

@Component
public class MascotasMapper {

   public MascotaResponseDTO toDTO(MascotaEntity mascota) {
        MascotaResponseDTO dto = new MascotaResponseDTO();
        dto.setId(mascota.getId());
        dto.setNombre(mascota.getNombre());
        dto.setTipo(mascota.getTipo());
        dto.setEdad(mascota.getEdad());
        dto.setAdoptado(mascota.isAdoptado());
        return dto;
    }


    public MascotaEntity toEntity ( MascotaRequestDTO dto){
        MascotaEntity mascotaEntity = new MascotaEntity();
        mascotaEntity.setNombre(dto.getNombre());
        mascotaEntity.setTipo(dto.getTipo());
        mascotaEntity.setEdad(dto.getEdad());
        return mascotaEntity;
    }


    public List <MascotaResponseDTO> toDTOList(List<MascotaEntity>mascotaEntities){
        return  mascotaEntities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

    }


    public void updateEntity(MascotaEntity mascota, MascotaRequestDTO dto) {
        mascota.setNombre(dto.getNombre());
        mascota.setTipo(dto.getTipo());
        mascota.setEdad(dto.getEdad());
    }





    
}

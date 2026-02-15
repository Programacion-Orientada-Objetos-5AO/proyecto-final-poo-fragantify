package ar.edu.huergo.lbgonzalez.fragantify.service.mascotas;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.huergo.lbgonzalez.fragantify.dto.mascotas.MascotaRequestDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.mascotas.MascotaResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.mascota.MascotaEntity;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.mascotas.MascotasMapper;
import ar.edu.huergo.lbgonzalez.fragantify.repository.mascota.MascotaRepository;

@Service
@Transactional
public class MascotasService {
    private final MascotaRepository mascotaRepository;
    private final MascotasMapper mascotaMapper;

    public MascotasService(MascotaRepository mascotaRepository, MascotasMapper mascotaMapper) {
        this.mascotaRepository = mascotaRepository;
        this.mascotaMapper = mascotaMapper;
    }


    public MascotaResponseDTO crearMascota(MascotaRequestDTO  requestDTO ){

        MascotaEntity mascotaEntity = mascotaMapper.toEntity(requestDTO);
        MascotaEntity mascotaGuardada = mascotaRepository.save(mascotaEntity);
        return mascotaMapper.toDTO(mascotaGuardada);    
    }


    @Transactional(readOnly = true)
    public List<MascotaResponseDTO> listarMascotas() {
        List<MascotaEntity> mascotas = mascotaRepository.findAll();
        return mascotaMapper.toDTOList(mascotas);
    }

    @Transactional(readOnly = true)
    public MascotaResponseDTO obtenerPorId(Long id) {
        MascotaEntity mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
        return mascotaMapper.toDTO(mascota);
    }

    public MascotaResponseDTO actualizarMascota(Long id, MascotaRequestDTO requestDto) {
        MascotaEntity mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));

        mascotaMapper.updateEntity(mascota, requestDto);
        MascotaEntity actualizada = mascotaRepository.save(mascota);
        return mascotaMapper.toDTO(actualizada);
    }

    public void eliminarMascota(Long id) {
        if (!mascotaRepository.existsById(id)) {
            throw new RuntimeException("Mascota no encontrada con id: " + id);
        }
        mascotaRepository.deleteById(id);
    }

    public MascotaResponseDTO marcarComoAdoptada(Long id) {
        MascotaEntity mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id: " + id));
        
        mascota.setAdoptado(true);
        MascotaEntity actualizada = mascotaRepository.save(mascota);
        return mascotaMapper.toDTO(actualizada);
    }









    
}

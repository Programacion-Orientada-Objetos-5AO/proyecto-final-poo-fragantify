package com.example.demo.service;

import com.example.demo.dto.PerfumeDto;
import com.example.demo.entity.Perfume;
import com.example.demo.repository.PerfumeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;

    // Obtener todos los perfumes
    public List<PerfumeDto> getPerfumes() {
        return perfumeRepository.findAll()
                .stream()
                .map(p -> new PerfumeDto(
                        p.getId(),
                        p.getNombre(),
                        p.getMarca(),
                        p.getPrecio()
                ))
                .toList();
    }

    // Obtener perfume por ID
    public Optional<Perfume> getPerfume(Long id) {
        return perfumeRepository.findById(id);
    }

    // Crear perfume
    public void crearPerfume(PerfumeDto perfumeDto) {
        perfumeRepository.save(new Perfume(
                perfumeDto.getNombre(),
                perfumeDto.getMarca(),
                perfumeDto.getPrecio()
        ));
    }

    // Actualizar perfume
    public void actualizarPerfume(Long id, PerfumeDto perfumeDto) {
        Perfume perfume = perfumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfume no encontrado con id " + id));

        perfume.setNombre(perfumeDto.getNombre());
        perfume.setMarca(perfumeDto.getMarca());
        perfume.setPrecio(perfumeDto.getPrecio());

        perfumeRepository.save(perfume);
    }

    // Eliminar perfume
    public void eliminarPerfume(Long id) {
        perfumeRepository.deleteById(id);
    }
}

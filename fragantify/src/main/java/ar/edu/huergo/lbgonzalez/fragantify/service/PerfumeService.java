package ar.edu.huergo.lbgonzalez.fragantify.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Marca;
import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.repository.MarcaRepository;
import ar.edu.huergo.lbgonzalez.fragantify.repository.PerfumeRepository;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Perfume> obtenerTodosLosPerfumes() {
        return perfumeRepository.findAll();
    }

    public Perfume obtenerPerfumePorId(Long id) {
        Optional<Perfume> perfume = perfumeRepository.findById(id);
        return perfume.orElseThrow(() -> new RuntimeException("Perfume not found"));
    }

    public Perfume crearPerfume(Perfume perfume, List<Long> ingredientesIds) {
        // For now, ignore ingredientesIds as entity doesn't have it
        if (perfume.getMarca() != null && perfume.getMarca().getId() != null) {
            Marca marca = marcaRepository.findById(perfume.getMarca().getId()).orElseThrow(() -> new RuntimeException("Marca not found"));
            perfume.setMarca(marca);
        }
        return perfumeRepository.save(perfume);
    }

    public Perfume actualizarPerfume(Long id, Perfume perfume, List<Long> ingredientesIds) {
        Perfume existing = obtenerPerfumePorId(id);
        existing.setNombre(perfume.getNombre());
        existing.setTipo(perfume.getTipo());
        existing.setFamiliaOlfativa(perfume.getFamiliaOlfativa());
        existing.setPrecio(perfume.getPrecio());
        if (perfume.getMarca() != null && perfume.getMarca().getId() != null) {
            Marca marca = marcaRepository.findById(perfume.getMarca().getId()).orElseThrow(() -> new RuntimeException("Marca not found"));
            existing.setMarca(marca);
        }
        // Ignore ingredientesIds
        return perfumeRepository.save(existing);
    }

    public void eliminarPerfume(Long id) {
        perfumeRepository.deleteById(id);
    }

    public List<Perfume> obtenerPerfumesPorMarca(String marcaNombre) {
        return perfumeRepository.findByMarcaNombre(marcaNombre);
    }

    public List<Perfume> filtrarPerfumes(String familiaOlfativa, Double precioMin, Double precioMax) {
        return perfumeRepository.findByFilters(familiaOlfativa, precioMin, precioMax);
    }

    public List<Perfume> compararPerfumes(List<Long> ids) {
        return perfumeRepository.findAllById(ids);
    }

    public Perfume toggleFavorito(Long id) {
        Perfume perfume = obtenerPerfumePorId(id);
        perfume.setFavorito(!perfume.getFavorito());
        return perfumeRepository.save(perfume);
    }
}

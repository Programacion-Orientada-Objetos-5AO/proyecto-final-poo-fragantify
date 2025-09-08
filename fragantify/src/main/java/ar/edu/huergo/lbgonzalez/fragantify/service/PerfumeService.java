package ar.edu.huergo.lbgonzalez.fragantify.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.repository.PerfumeRepository;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;

    // Obtener todos los perfumes
    public List<Perfume> getPerfumes() {
        return perfumeRepository.findAll();
    }

    // Obtener perfume por ID
    public Optional<Perfume> getPerfume(Long id) {
        return perfumeRepository.findById(id);
    }

    // Crear perfume
    public Perfume crearPerfume(Perfume perfume) {
        return perfumeRepository.save(perfume);
    }

    // Actualizar perfume
    public Perfume actualizarPerfume(Long id, Perfume perfume) {
        Perfume existing = perfumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfume no encontrado con id " + id));
        existing.setNombre(perfume.getNombre());
        existing.setMarca(perfume.getMarca());
        existing.setPrecio(perfume.getPrecio());
        return perfumeRepository.save(existing);
    }

    // Eliminar perfume
    public void eliminarPerfume(Long id) {
        perfumeRepository.deleteById(id);
    }

    // Filtrar perfumes
    public List<Perfume> filtrarPerfumes(String familiaOlfativa, Double precioMin, Double precioMax) {
        // Implementar lógica de filtrado, por ahora devolver todos
        return perfumeRepository.findAll();
    }

    // Obtener perfumes por marca
    public List<Perfume> obtenerPerfumesPorMarca(String marca) {
        // Implementar lógica, por ahora devolver todos
        return perfumeRepository.findAll();
    }

    // Comparar perfumes
    public List<Perfume> compararPerfumes(List<Long> ids) {
        return perfumeRepository.findAllById(ids);
    }

    // Toggle favorito
    public Perfume toggleFavorito(Long id) {
        Perfume perfume = getPerfume(id).orElseThrow(() -> new RuntimeException("Perfume no encontrado con id " + id));
        // Asumir que hay un campo favorito, por ahora no cambiar
        return perfumeRepository.save(perfume);
    }
}

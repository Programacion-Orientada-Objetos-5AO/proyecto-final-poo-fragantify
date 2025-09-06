package ar.edu.huergo.lbgonzalez.fragantify.service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.repository.PerfumeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;

    // Obtener todos los perfumes
    public List<Perfume> obtenerTodosLosPerfumes() {
        return perfumeRepository.findAll();
    }

    // Obtener perfume por ID
    public Perfume obtenerPerfumePorId(Long id) {
        return perfumeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfume no encontrado con id " + id));
    }

    // Crear perfume
    public Perfume crearPerfume(Perfume perfume, Object something) {
        return perfumeRepository.save(perfume);
    }

    // Actualizar perfume
    public Perfume actualizarPerfume(Long id, Perfume perfume, Object something) {
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
        Perfume perfume = obtenerPerfumePorId(id);
        // Asumir que hay un campo favorito, por ahora no cambiar
        return perfumeRepository.save(perfume);
    }
}

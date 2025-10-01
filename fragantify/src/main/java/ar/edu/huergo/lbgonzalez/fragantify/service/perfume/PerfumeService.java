package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.exception.PerfumeNotFoundException;
import ar.edu.huergo.lbgonzalez.fragantify.repository.perfume.PerfumeRepository;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Autowired
    private Validator validator;

    // Obtener todos los perfumes
    public List<Perfume> getPerfumes() {
        return perfumeRepository.findAll();
    }

    // Obtener perfume por ID
    public Optional<Perfume> getPerfume(Long id) {
        return perfumeRepository.findById(id);
    }

    // Crear perfume con validacion
    public Perfume crearPerfume(@Valid Perfume perfume) {
        validatePerfume(perfume);
        return perfumeRepository.save(perfume);
    }

    // Actualizar perfume con validacion
    public Perfume actualizarPerfume(Long id, @Valid Perfume perfume) {
        validatePerfume(perfume);
        Perfume existing = perfumeRepository.findById(id)
                .orElseThrow(() -> new PerfumeNotFoundException("Perfume no encontrado con id " + id));
        existing.setNombre(perfume.getNombre());
        existing.setMarca(perfume.getMarca());
        existing.setPrecio(perfume.getPrecio());
        existing.setFamiliaOlfativa(perfume.getFamiliaOlfativa());
        return perfumeRepository.save(existing);
    }

    // Eliminar perfume
    public void eliminarPerfume(Long id) {
        if (!perfumeRepository.existsById(id)) {
            throw new PerfumeNotFoundException("Perfume no encontrado con id " + id);
        }
        perfumeRepository.deleteById(id);
    }

    // Filtrar perfumes con validacion de parametros
    public List<Perfume> filtrarPerfumes(String familiaOlfativa, Double precioMin, Double precioMax) {
        if (precioMin != null && precioMax != null && precioMin > precioMax) {
            throw new IllegalArgumentException("precioMin no puede ser mayor que precioMax");
        }

        List<Perfume> base = (familiaOlfativa != null && !familiaOlfativa.trim().isEmpty())
            ? perfumeRepository.findByFamiliaOlfativaContainingIgnoreCase(familiaOlfativa.trim())
            : perfumeRepository.findAll();

        return base.stream()
            .filter(p -> precioMin == null || p.getPrecio() >= precioMin)
            .filter(p -> precioMax == null || p.getPrecio() <= precioMax)
            .collect(Collectors.toList());
    }

    // Buscar perfumes por nombre
    public List<Perfume> buscarPerfumesPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser vacio");
        }
        return perfumeRepository.findByNombreContainingIgnoreCase(nombre.trim());
    }
    // Obtener perfumes por marca
    public List<Perfume> obtenerPerfumesPorMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("La marca no puede ser vacia");
        }
        return perfumeRepository.findByMarcaContainingIgnoreCase(marca);
    }

    // Comparar perfumes
    public List<Perfume> compararPerfumes(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("La lista de IDs no puede estar vacia");
        }
        return perfumeRepository.findAllById(ids);
    }

    // Toggle favorito
    public Perfume toggleFavorito(Long id) {
        Perfume perfume = getPerfume(id).orElseThrow(() -> new PerfumeNotFoundException("Perfume no encontrado con id " + id));
        // Asumir que hay un campo favorito, por ahora no cambiar
        return perfumeRepository.save(perfume);
    }

    // Validar entidad Perfume
    private void validatePerfume(Perfume perfume) {
        Set<ConstraintViolation<Perfume>> violations = validator.validate(perfume);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Perfume> violation : violations) {
                sb.append(violation.getPropertyPath()).append(": ").append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException("Errores de validacion: " + sb.toString());
        }
    }
}


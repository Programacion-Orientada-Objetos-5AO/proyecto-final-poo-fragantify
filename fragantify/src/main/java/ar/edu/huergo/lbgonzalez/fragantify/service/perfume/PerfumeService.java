package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.exception.PerfumeNotFoundException;
import ar.edu.huergo.lbgonzalez.fragantify.repository.perfume.PerfumeRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@Service
public class PerfumeService {

    @Autowired
    private PerfumeRepository perfumeRepository;

    @Autowired(required = false)
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
        // Implementar logica de filtrado real aqui, por ahora devolver todos
        return perfumeRepository.findAll();
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
        perfume.setFavorito(!perfume.isFavorito());
        return perfumeRepository.save(perfume);
    }

    // Validar entidad Perfume (fallback si el Validator no es inyectado en tests)
    private void validatePerfume(Perfume perfume) {
        if (validator == null) {
            jakarta.validation.ValidatorFactory factory = jakarta.validation.Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        }
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


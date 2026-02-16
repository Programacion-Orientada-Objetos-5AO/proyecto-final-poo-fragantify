package ar.edu.huergo.lbgonzalez.fragantify.service.marcas;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.marca.Marca;
import ar.edu.huergo.lbgonzalez.fragantify.repository.marca.MarcasRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarcasService {

    private final MarcasRepository marcasRepository;
    private final Validator validator; 

    public List<Marca> getMarcas() {
        return marcasRepository.findAll();
    }

    public Optional<Marca> getMarca(Long id) {
        return marcasRepository.findById(id);
    }

    public Marca crearMarca(@Valid Marca marca) {
        validateMarca(marca);
        return marcasRepository.save(marca);
    }

    public Marca actualizarMarca(Long id, @Valid Marca marca) {
        validateMarca(marca);
        Marca existing = marcasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id " + id));

        existing.setNombre(marca.getNombre());
        existing.setPaisOrigen(marca.getPaisOrigen());
        existing.setAñoFundacion(marca.getAñoFundacion());
        existing.setDescripcion(marca.getDescripcion());

        return marcasRepository.save(existing);
    }

    public void eliminarMarca(Long id) {
        if (!marcasRepository.existsById(id)) {
            throw new RuntimeException("Marca no encontrada con id " + id);
        }
        marcasRepository.deleteById(id);
    }

    private void validateMarca(Marca marca) {
        Set<ConstraintViolation<Marca>> violations = validator.validate(marca);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}

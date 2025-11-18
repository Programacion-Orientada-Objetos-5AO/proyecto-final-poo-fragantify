package ar.edu.huergo.lbgonzalez.fragantify.service.tareas;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.dto.tareas.ResumenCreadorDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.tareas.Tarea;
import ar.edu.huergo.lbgonzalez.fragantify.repository.tareas.TareaRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TareaService {

    private final TareaRepository tareaRepository;
    private final Validator validator;

    public List<Tarea> listar() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> buscar(Long id) {
        return tareaRepository.findById(id);
    }

    public Tarea crear(@Valid Tarea tarea) {
        validar(tarea);
        return tareaRepository.save(tarea);
    }

    public Tarea actualizar(Long id, @Valid Tarea datos) {
        Tarea existente = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id " + id));
        existente.setTitulo(datos.getTitulo());
        existente.setDescripcion(datos.getDescripcion());
        existente.setCreador(datos.getCreador());
        validar(existente);
        return tareaRepository.save(existente);
    }

    public void eliminar(Long id) {
        if (!tareaRepository.existsById(id)) {
            throw new RuntimeException("Tarea no encontrada con id " + id);
        }
        tareaRepository.deleteById(id);
    }

    public Tarea marcarCompletada(Long id) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id " + id));
        tarea.setCompletada(true);
        return tareaRepository.save(tarea);
    }

    public ResumenCreadorDTO resumenPorCreador(String creador) {
        List<Tarea> tareas = tareaRepository.findByCreador(creador);
        int total = tareas.size();
        int completadas = (int) tareas.stream().filter(Tarea::isCompletada).count();
        int pendientes = total - completadas;
        double porcentaje = total == 0 ? 0.0 : (completadas * 100.0) / total;
        return new ResumenCreadorDTO(creador, total, completadas, pendientes, porcentaje);
    }

    private void validar(Tarea tarea) {
        java.util.Set<ConstraintViolation<Tarea>> violaciones = validator.validate(tarea);
        if (!violaciones.isEmpty()) {
            throw new ConstraintViolationException(violaciones);
        }
    }
}

package ar.edu.huergo.lbgonzalez.fragantify.mapper.tareas;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.lbgonzalez.fragantify.dto.tareas.TareaRequestDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.tareas.TareaResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.tareas.Tarea;

@Component
public class TareaMapper {

    public TareaResponseDTO toDTO(Tarea tarea) {
        return new TareaResponseDTO(
                tarea.getId(),
                tarea.getTitulo(),
                tarea.getDescripcion(),
                tarea.getCreador(),
                tarea.isCompletada());
    }

    public List<TareaResponseDTO> toDTOList(List<Tarea> tareas) {
        return tareas.stream().map(this::toDTO).toList();
    }

    public Tarea toEntity(TareaRequestDTO dto) {
        Tarea tarea = new Tarea();
        tarea.setTitulo(dto.titulo());
        tarea.setDescripcion(dto.descripcion());
        tarea.setCreador(dto.creador());
        tarea.setCompletada(false);
        return tarea;
    }

    public void updateEntity(Tarea tarea, TareaRequestDTO dto) {
        tarea.setTitulo(dto.titulo());
        tarea.setDescripcion(dto.descripcion());
        tarea.setCreador(dto.creador());
    }
}

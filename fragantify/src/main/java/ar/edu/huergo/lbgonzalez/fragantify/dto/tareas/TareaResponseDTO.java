package ar.edu.huergo.lbgonzalez.fragantify.dto.tareas;

public record TareaResponseDTO(
        Long id,
        String titulo,
        String descripcion,
        String creador,
        boolean completada) {
}

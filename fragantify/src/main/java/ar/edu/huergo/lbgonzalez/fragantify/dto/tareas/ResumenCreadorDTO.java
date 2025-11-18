package ar.edu.huergo.lbgonzalez.fragantify.dto.tareas;

public record ResumenCreadorDTO(
        String nombreCreador,
        Integer totalTareas,
        Integer tareasCompletadas,
        Integer tareasPendientes,
        Double porcentajeCompletado) {
}

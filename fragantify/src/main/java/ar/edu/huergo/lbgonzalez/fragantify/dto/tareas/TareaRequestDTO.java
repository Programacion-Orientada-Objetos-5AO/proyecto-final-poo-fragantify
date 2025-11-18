package ar.edu.huergo.lbgonzalez.fragantify.dto.tareas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TareaRequestDTO(
        @NotBlank @Size(max = 255) String titulo,
        @NotBlank @Size(max = 1000) String descripcion,
        @NotBlank @Size(max = 255) String creador) {
}

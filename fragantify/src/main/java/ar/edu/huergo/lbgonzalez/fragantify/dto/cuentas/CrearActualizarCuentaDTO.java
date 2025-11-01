package ar.edu.huergo.lbgonzalez.fragantify.dto.cuentas;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CrearActualizarCuentaDTO {
  @NotBlank @Size(min=2, max=100)
  private String nombre;

  @Email @NotBlank @Size(max=100)
  private String mail;

  @NotBlank @Size(min=12, max=100)
  private String contraseña;

  @Positive @Min(12)
  private int edad;

  @NotNull
  private ar.edu.huergo.lbgonzalez.fragantify.entity.cuentas.Cuentas.TipoCuenta tipo;
}

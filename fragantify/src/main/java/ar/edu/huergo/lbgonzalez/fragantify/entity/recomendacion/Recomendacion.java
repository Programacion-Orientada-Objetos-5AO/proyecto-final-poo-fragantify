package ar.edu.huergo.lbgonzalez.fragantify.entity.recomendacion;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

import ar.edu.huergo.lbgonzalez.fragantify.entity.cuentas.Cuentas;
import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;

@Entity
@Table(name = "recomendacion")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Recomendacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "cuenta_id", nullable = false)
  private Cuentas cuenta;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "perfume_id", nullable = false)
  private Perfume perfume;

  @Min(value = 0,message = "La puntuación mínima es 0") 
  @Max(value = 10,message = "La puntuación máxima es 10")
  private int puntuacion;

  @NotBlank @Size(max = 500, message = "El comentario no puede exceder los 500 caracteres")
  private String comentario;

  @PastOrPresent(message = "La fecha no puede ser futura y tiene que estar en formato AAAA-MM-DD")
  private LocalDate fecha;
}

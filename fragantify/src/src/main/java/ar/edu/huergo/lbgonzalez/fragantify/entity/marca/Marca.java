package ar.edu.huergo.lbgonzalez.fragantify.entity.marca;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marca ")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Marca {
    public Marca(String nombre2, String paisOrigen2, int añoFundacion2, String descripcion2) {
    }
  //-idMarca
    //-nombre
    //-paisOrigen
    //-añoFundacion
    //-descripcion
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100)
    private String nombre;

    @NotBlank(message = "El pais de origen es obligatorio")
    @Size(min = 2, max = 100)
    private String paisOrigen;

    @Positive(message = "El año de fundacion debe ser mayor a 0")
    @Max(value = 2024, message = "El año de fundacion no puede ser mayor al año actual")
    private int añoFundacion;
    @Size(max = 500)
    private String descripcion;
}


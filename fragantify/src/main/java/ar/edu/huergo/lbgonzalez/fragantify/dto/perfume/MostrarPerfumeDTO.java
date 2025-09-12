package ar.edu.huergo.lbgonzalez.fragantify.dto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MostrarPerfumeDTO extends PerfumeDto {
    private String categoriaNombre;

    public MostrarPerfumeDTO(Long id, String nombre, String marca, Double precio, String categoriaNombre) {
        super(id, nombre, marca, precio);
        this.categoriaNombre = categoriaNombre;
    }
}



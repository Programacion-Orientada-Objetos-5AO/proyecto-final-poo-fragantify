package ar.edu.huergo.lbgonzalez.fragantify.dto.perfume;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MostrarPerfumeDTO extends PerfumeDto {

    public MostrarPerfumeDTO(Long id, String nombre, String marca, Double precio ) {
        super(id, nombre, marca, precio);
    }
}



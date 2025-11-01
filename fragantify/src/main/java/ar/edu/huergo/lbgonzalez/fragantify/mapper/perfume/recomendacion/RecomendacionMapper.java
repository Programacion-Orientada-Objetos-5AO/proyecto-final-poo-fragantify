package ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.recomendacion;

import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.huergo.lbgonzalez.fragantify.dto.recomendacion.CrearActualizarRecomendacionDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.recomendacion.MostrarRecomendacionDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.recomendacion.Recomendacion;

@Component
public class RecomendacionMapper {

    public MostrarRecomendacionDTO toDTO(Recomendacion r) {
        if (r == null) {
            return null;
        }
        // OJO: asumo que cuenta y perfume no son null (relaciones obligatorias).
        return new MostrarRecomendacionDTO(
                r.getId(),
                r.getCuenta().getId(),
                r.getPerfume().getId(),
                r.getPuntuacion(),
                r.getComentario(),
                r.getFecha(),
                r.getCuenta().getNombre(),
                r.getPerfume().getNombre()
        );
    }

    public List<MostrarRecomendacionDTO> toDTOList(List<Recomendacion> recomendaciones) {
        if (recomendaciones == null) {
            return List.of();
        }
        return recomendaciones.stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Mapea solo campos simples del request; las relaciones (cuenta/perfume)
     * se resuelven en el Service usando los IDs de dto.getCuentaId()/getPerfumeId().
     */
    public Recomendacion toEntity(CrearActualizarRecomendacionDTO dto) {
        if (dto == null) {
            return null;
        }
        Recomendacion r = new Recomendacion();
        r.setPuntuacion(dto.getPuntuacion());
        r.setComentario(dto.getComentario());
        r.setFecha(dto.getFecha());
        // r.setCuenta(...) y r.setPerfume(...) los setea el Service
        return r;
    }
}

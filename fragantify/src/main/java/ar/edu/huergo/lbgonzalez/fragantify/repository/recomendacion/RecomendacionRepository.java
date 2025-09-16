package ar.edu.huergo.lbgonzalez.fragantify.repository.recomendacion;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ar.edu.huergo.lbgonzalez.fragantify.entity.recomendacion.Recomendacion;

public interface RecomendacionRepository extends JpaRepository<Recomendacion, Long> {
  List<Recomendacion> findByCuenta_Id(Long cuentaId);
  List<Recomendacion> findByPerfume_Id(Long perfumeId);
}

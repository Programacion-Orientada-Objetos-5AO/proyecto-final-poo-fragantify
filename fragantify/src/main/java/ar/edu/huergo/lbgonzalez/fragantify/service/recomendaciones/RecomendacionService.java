package ar.edu.huergo.lbgonzalez.fragantify.service.recomendaciones;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ar.edu.huergo.lbgonzalez.fragantify.entity.cuentas.Cuentas;
import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.entity.recomendacion.Recomendacion;
import ar.edu.huergo.lbgonzalez.fragantify.repository.perfume.PerfumeRepository;
import ar.edu.huergo.lbgonzalez.fragantify.repository.recomendacion.RecomendacionRepository;
import ar.edu.huergo.lbgonzalez.fragantify.repository.usuarios.CuentasRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecomendacionService {

    private final RecomendacionRepository repo;
    private final CuentasRepository cuentasRepo;
    private final PerfumeRepository perfumeRepo;

    public List<Recomendacion> listar() {
        return repo.findAll();
    }

    public Optional<Recomendacion> obtener(Long id) {
        return repo.findById(id);
    }

    public Recomendacion crear(Recomendacion r, Long cuentaId, Long perfumeId) {
        Cuentas cuenta = cuentasRepo.findById(cuentaId)
            .orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + cuentaId));
        Perfume perfume = perfumeRepo.findById(perfumeId)
            .orElseThrow(() -> new RuntimeException("Perfume no encontrado: " + perfumeId));

        r.setCuenta(cuenta);
        r.setPerfume(perfume);
        return repo.save(r);
    }

    public Recomendacion actualizar(Long id, Recomendacion data, Long cuentaId, Long perfumeId) {
        Recomendacion existente = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Recomendación no encontrada: " + id));

        if (cuentaId != null) {
            Cuentas cuenta = cuentasRepo.findById(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada: " + cuentaId));
            existente.setCuenta(cuenta);
        }

        if (perfumeId != null) {
            Perfume perfume = perfumeRepo.findById(perfumeId)
                .orElseThrow(() -> new RuntimeException("Perfume no encontrado: " + perfumeId));
            existente.setPerfume(perfume);
        }

        existente.setPuntuacion(data.getPuntuacion());
        existente.setComentario(data.getComentario());
        existente.setFecha(data.getFecha());

        return repo.save(existente);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public List<Recomendacion> porCuenta(Long cuentaId) {
        return repo.findByCuenta_Id(cuentaId);
    }

    public List<Recomendacion> porPerfume(Long perfumeId) {
        return repo.findByPerfume_Id(perfumeId);
    }
}

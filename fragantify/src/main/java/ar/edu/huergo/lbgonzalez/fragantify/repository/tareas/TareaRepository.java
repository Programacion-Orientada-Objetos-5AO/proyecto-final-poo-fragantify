package ar.edu.huergo.lbgonzalez.fragantify.repository.tareas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.tareas.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByCreador(String creador);
}

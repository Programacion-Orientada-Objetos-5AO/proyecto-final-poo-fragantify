package ar.edu.huergo.lbgonzalez.fragantify.repository.usuarios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.cuentas.Cuentas;

@Repository
public interface CuentasRepository extends JpaRepository<Cuentas, Long> {

    // Busca perfumes cuyo nombre contenga cierto texto, sin importar mayúsculas
    List<Cuentas> findByNombreContainingIgnoreCase(String nombre);



}

    

package ar.edu.huergo.lbgonzalez.fragantify.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Tests de validación para la entidad Perfume.
 * Campos en Perfume.java:
 *  - nombre: @NotBlank "El nombre es obligatorio", @Size(2..100)
 *  - marca:  @NotBlank "La marca es obligatoria", @Size(2..100)
 *  - precio: @Positive "El precio debe ser mayor a 0"
 */
@DisplayName("Tests de Validación - Entidad Perfume")
class PerfumeValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Perfume crearValido() {
        Perfume p = new Perfume();
        p.setNombre("Fragancia X");
        p.setMarca("Marca Y");
        p.setPrecio(1500.0);
        return p;
    }

    @Test
    @DisplayName("Debería validar perfume correcto sin errores")
    void deberiaValidarPerfumeCorrectoSinErrores() {
        Perfume p = crearValido();
        Set<ConstraintViolation<Perfume>> violaciones = validator.validate(p);
        assertTrue(violaciones.isEmpty(), "No debería haber violaciones para un perfume válido");
    }

    // ---- nombre ----
    @Test
    @DisplayName("Debería fallar validación con nombre null")
    void deberiaFallarConNombreNull() {
        Perfume p = crearValido();
        p.setNombre(null);
        var violaciones = validator.validate(p);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("obligatorio")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre vacío")
    void deberiaFallarConNombreVacio() {
        Perfume p = crearValido();
        p.setNombre("");
        var violaciones = validator.validate(p);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre solo espacios")
    void deberiaFallarConNombreSoloEspacios() {
        Perfume p = crearValido();
        p.setNombre("   ");
        var violaciones = validator.validate(p);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"A"})
    @DisplayName("Debería fallar validación con nombres muy cortos")
    void deberiaFallarConNombresMuyCortos(String nombreCorto) {
        Perfume p = crearValido();
        p.setNombre(nombreCorto);
        var violaciones = validator.validate(p);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería fallar validación con nombre muy largo (>100)")
    void deberiaFallarConNombreMuyLargo() {
        Perfume p = crearValido();
        p.setNombre("A".repeat(101));
        var violaciones = validator.validate(p);
        assertFalse(violaciones.isEmpty());
        assertTrue(violaciones.stream().anyMatch(v -> v.getPropertyPath().toString().equals("nombre")));
        assertTrue(violaciones.stream().anyMatch(v -> v.getMessage().contains("entre 2 y 100 caracteres")));
    }

    @Test
    @DisplayName("Debería aceptar nombres en el límite válido (2 y 100)")
    void deberiaAceptarNombreEnLimites() {
        Perfume p1 = crearValido(); p1.setNombre("AB");           // 2
        Perfume p2 = crearValido(); p2.setNombre("A".repeat(100)); // 100
        assertTrue(validator.validate(p1).isEmpty());
        assertTrue(validator.validate(p2).isEmpty());
    }
}
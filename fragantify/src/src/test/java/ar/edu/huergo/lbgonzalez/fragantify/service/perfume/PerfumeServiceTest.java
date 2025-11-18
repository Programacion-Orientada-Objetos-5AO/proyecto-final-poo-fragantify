package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.repository.perfume.PerfumeRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Tests de unidad para PerfumeService
 *
 * CONCEPTOS DEMOSTRADOS:
 * 1. @ExtendWith(MockitoExtension.class) - Habilita el uso de Mockito en JUnit
 * 2. @Mock - Crea objetos mock (simulados) de las dependencias
 * 3. @InjectMocks - Inyecta los mocks en la clase bajo prueba
 * 4. @BeforeEach - Método que se ejecuta antes de cada test
 * 5. @DisplayName - Nombres descriptivos para los tests
 * 6. when().thenReturn() - Define el comportamiento de los mocks
 * 7. verify() - Verifica que se llamaron métodos específicos
 * 8. Assertions - Verificaciones del resultado esperado
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PerfumeService")
class PerfumeServiceTest {

    @Mock
    private PerfumeRepository perfumeRepository;

    @InjectMocks
    private PerfumeService perfumeService;

    private Perfume perfumeEjemplo;

    @BeforeEach // Se ejecuta antes de cada test
    void setUp() {
        perfumeEjemplo = new Perfume();
        perfumeEjemplo.setId(1L);
        perfumeEjemplo.setNombre("Aroma Supremo");
        perfumeEjemplo.setMarca("Fragantify");
        perfumeEjemplo.setPrecio(155.50);
        perfumeEjemplo.setFamiliaOlfativa("Amaderada");
    }

    @Test
    @DisplayName("Debería obtener todos los perfumes correctamente")
    void deberiaObtenerTodosLosPerfumes() {
        // Given
        List<Perfume> perfumesEsperados = Arrays.asList(perfumeEjemplo);
        when(perfumeRepository.findAll()).thenReturn(perfumesEsperados);

        // When
        List<Perfume> resultado = perfumeService.getPerfumes();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(perfumeEjemplo.getNombre(), resultado.get(0).getNombre());
        verify(perfumeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener un perfume por ID cuando existe")
    void deberiaObtenerPerfumePorIdCuandoExiste() {
        // Given
        Long perfumeId = 1L;
        when(perfumeRepository.findById(perfumeId)).thenReturn(Optional.of(perfumeEjemplo));

        // When
        Perfume resultado = perfumeService.getPerfume(perfumeId)
                .orElseThrow(() -> new EntityNotFoundException("Perfume no encontrado"));

        // Then
        assertNotNull(resultado);
        assertEquals(perfumeEjemplo.getId(), resultado.getId());
        assertEquals(perfumeEjemplo.getNombre(), resultado.getNombre());
        verify(perfumeRepository, times(1)).findById(perfumeId);
    }

    @Test
    @DisplayName("Debería lanzar EntityNotFoundException cuando el perfume no existe")
    void deberiaLanzarExcepcionCuandoPerfumeNoExiste() {
        // Given
        Long perfumeIdInexistente = 999L;
        when(perfumeRepository.findById(perfumeIdInexistente)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException excepcion = assertThrows(
                EntityNotFoundException.class,
                () -> perfumeService.getPerfume(perfumeIdInexistente)
                        .orElseThrow(() -> new EntityNotFoundException("Perfume no encontrado"))
        );

        assertEquals("Perfume no encontrado", excepcion.getMessage());
        verify(perfumeRepository, times(1)).findById(perfumeIdInexistente);
    }

    @Test
    @DisplayName("Debería crear un perfume correctamente")
    void deberiaCrearPerfumeCorrectamente() {
        // Given
        Perfume nuevoPerfume = new Perfume("Cítrico Boreal", "Marca X", 120.00, "Cítrica");
        when(perfumeRepository.save(any(Perfume.class))).thenReturn(nuevoPerfume);

        // When
        Perfume resultado = perfumeService.crearPerfume(nuevoPerfume);

        // Then
        assertNotNull(resultado);
        assertEquals(nuevoPerfume.getNombre(), resultado.getNombre());
        verify(perfumeRepository, times(1)).save(nuevoPerfume);
    }

    @Test
    @DisplayName("Debería actualizar un perfume existente correctamente")
    void deberiaActualizarPerfumeExistente() {
        // Given
        Long perfumeId = 1L;
        Perfume perfumeActualizado = new Perfume("Ámbar Nocturno", "Marca Y", 180.00, "Oriental");

        when(perfumeRepository.findById(perfumeId)).thenReturn(Optional.of(perfumeEjemplo));
        when(perfumeRepository.save(any(Perfume.class))).thenReturn(perfumeEjemplo);

        // When
        Perfume resultado = perfumeService.actualizarPerfume(perfumeId, perfumeActualizado);

        // Then
        assertNotNull(resultado);
        verify(perfumeRepository, times(1)).findById(perfumeId);
        verify(perfumeRepository, times(1)).save(perfumeEjemplo);

        // Verificar que los campos se actualizaron
        assertEquals(perfumeActualizado.getNombre(), perfumeEjemplo.getNombre());
        assertEquals(perfumeActualizado.getMarca(), perfumeEjemplo.getMarca());
        assertEquals(perfumeActualizado.getPrecio(), perfumeEjemplo.getPrecio());
        assertEquals(perfumeActualizado.getFamiliaOlfativa(), perfumeEjemplo.getFamiliaOlfativa());
    }
}

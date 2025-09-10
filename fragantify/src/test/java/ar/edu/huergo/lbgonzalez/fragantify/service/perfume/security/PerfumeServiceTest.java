package ar.edu.huergo.lbgonzalez.fragantify.service.perfume.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;
import ar.edu.huergo.lbgonzalez.fragantify.exception.PerfumeNotFoundException;
import ar.edu.huergo.lbgonzalez.fragantify.repository.PerfumeRepository;
import ar.edu.huergo.lbgonzalez.fragantify.service.PerfumeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests de Unidad - PerfumeService (Mockito)")
class PerfumeServiceTest {

    @Mock
    private PerfumeRepository perfumeRepository;

    @Mock
    private jakarta.validation.Validator validator;

    @InjectMocks
    private PerfumeService perfumeService;

    private Perfume p1;
    private Perfume p2;

    @BeforeEach
    void setUp() {
        p1 = new Perfume("Tommy", "Tommy Hilfiger", 150.0);
        p1.setId(1L);
        p2 = new Perfume("Acqua di Gio", "Giorgio Armani", 250.0);
        p2.setId(2L);
        when(validator.validate(any())).thenReturn(java.util.Set.of());
    }

    @Test
    @DisplayName("Debería obtener todos los perfumes")
    void deberiaObtenerTodosLosPerfumes() {
        when(perfumeRepository.findAll()).thenReturn(List.of(p1, p2));

        var resultado = perfumeService.getPerfumes();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(perfumeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Debería obtener un perfume por ID cuando existe")
    void deberiaObtenerPerfumePorIdCuandoExiste() {
        when(perfumeRepository.findById(1L)).thenReturn(Optional.of(p1));

        var opt = perfumeService.getPerfume(1L);

        assertTrue(opt.isPresent());
        assertEquals("Tommy", opt.get().getNombre());
        verify(perfumeRepository).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar Optional.empty cuando no existe el perfume")
    void deberiaRetornarEmptyCuandoNoExiste() {
        when(perfumeRepository.findById(99L)).thenReturn(Optional.empty());

        var opt = perfumeService.getPerfume(99L);

        assertTrue(opt.isEmpty());
        verify(perfumeRepository).findById(99L);
    }

    @Test
    @DisplayName("Debería crear un perfume correctamente")
    void deberiaCrearPerfumeCorrectamente() {
        var nuevo = new Perfume("Sauvage", "Dior", 300.0);
        when(perfumeRepository.save(nuevo)).thenReturn(nuevo);

        var creado = perfumeService.crearPerfume(nuevo);

        assertNotNull(creado);
        assertEquals("Sauvage", creado.getNombre());
        verify(perfumeRepository).save(nuevo);
    }

    @Test
    @DisplayName("Debería actualizar un perfume existente")
    void deberiaActualizarPerfumeExistente() {
        var cambios = new Perfume("Tommy (Updated)", "Tommy Hilfiger", 199.99);
        when(perfumeRepository.findById(1L)).thenReturn(Optional.of(p1));
        when(perfumeRepository.save(any(Perfume.class))).thenAnswer(inv -> inv.getArgument(0));

        var actualizado = perfumeService.actualizarPerfume(1L, cambios);

        assertEquals("Tommy (Updated)", actualizado.getNombre());
        assertEquals(199.99, actualizado.getPrecio());

        ArgumentCaptor<Perfume> captor = ArgumentCaptor.forClass(Perfume.class);
        verify(perfumeRepository).save(captor.capture());
        Perfume guardado = captor.getValue();
        assertEquals("Tommy (Updated)", guardado.getNombre());
        assertEquals("Tommy Hilfiger", guardado.getMarca());
        assertEquals(199.99, guardado.getPrecio());
    }

    @Test
    @DisplayName("Actualizar debería lanzar PerfumeNotFoundException si no existe el id")
    void actualizarDeberiaLanzarCuandoNoExiste() {
        when(perfumeRepository.findById(123L)).thenReturn(Optional.empty());

        var ex = assertThrows(PerfumeNotFoundException.class,
                () -> perfumeService.actualizarPerfume(123L, p1));

        assertTrue(ex.getMessage().contains("Perfume no encontrado"));
        verify(perfumeRepository).findById(123L);
        verify(perfumeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería eliminar un perfume por id")
    void deberiaEliminarPerfume() {
        perfumeService.eliminarPerfume(7L);
        verify(perfumeRepository).deleteById(7L);
    }

    @Test
    @DisplayName("Comparar perfumes debería delegar en findAllById")
    void compararPerfumes() {
        when(perfumeRepository.findAllById(List.of(1L, 2L))).thenReturn(List.of(p1, p2));

        var res = perfumeService.compararPerfumes(List.of(1L, 2L));

        assertEquals(2, res.size());
        verify(perfumeRepository).findAllById(List.of(1L, 2L));
    }

    @Test
    @DisplayName("Filtrar perfumes (stub actual) debería devolver findAll")
    void filtrarPerfumes_stubDevuelveTodo() {
        when(perfumeRepository.findAll()).thenReturn(List.of(p1, p2));

        var res = perfumeService.filtrarPerfumes("amaderada", 100.0, 300.0);

        assertEquals(2, res.size());
        verify(perfumeRepository).findAll();
    }

    @Test
    @DisplayName("Obtener por marca (stub actual) debería devolver findAll")
    void obtenerPorMarca_stubDevuelveTodo() {
        when(perfumeRepository.findAll()).thenReturn(List.of(p2));

        var res = perfumeService.obtenerPerfumesPorMarca("Armani");

        assertEquals(1, res.size());
        verify(perfumeRepository).findAll();
    }

    @Test
    @DisplayName("Toggle favorito: lanza si no existe")
    void toggleFavoritoNoExiste() {
        when(perfumeRepository.findById(999L)).thenReturn(Optional.empty());

        var ex = assertThrows(RuntimeException.class,
                () -> perfumeService.toggleFavorito(999L));

        assertTrue(ex.getMessage().contains("Perfume no encontrado"));
        verify(perfumeRepository).findById(999L);
        verify(perfumeRepository, never()).save(any());
    }

    @Test
    @DisplayName("Toggle favorito: guarda el perfume (stub actual no cambia campos)")
    void toggleFavoritoGuarda() {
        when(perfumeRepository.findById(2L)).thenReturn(Optional.of(p2));
        when(perfumeRepository.save(p2)).thenReturn(p2);

        var res = perfumeService.toggleFavorito(2L);

        assertNotNull(res);
        verify(perfumeRepository).save(p2);
    }
}

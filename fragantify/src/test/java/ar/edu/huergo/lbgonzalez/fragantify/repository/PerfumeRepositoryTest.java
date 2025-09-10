package ar.edu.huergo.lbgonzalez.fragantify.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import ar.edu.huergo.lbgonzalez.fragantify.entity.Perfume;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@DisplayName("Tests de Integración - PerfumeRepository")
class PerfumeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PerfumeRepository perfumeRepository;

    private Perfume p1;
    private Perfume p2;
    private Perfume p3;

    @BeforeEach
    void setUp() {
        // Seed: 3 perfumes
        p1 = new Perfume("Tommy", "Tommy Hilfiger", 150.0);
        p1 = entityManager.persistAndFlush(p1);

        p2 = new Perfume("Tom Ford Noir", "Tom Ford", 350.0);
        p2 = entityManager.persistAndFlush(p2);

        p3 = new Perfume("Acqua di Gio", "Giorgio Armani", 250.0);
        p3 = entityManager.persistAndFlush(p3);

        entityManager.clear();
    }

    @Test
    @DisplayName("Debería encontrar por nombre conteniendo texto (case-insensitive)")
    void deberiaEncontrarPorNombreContainingIgnoreCase() {
        List<Perfume> encontrados = perfumeRepository.findByNombreContainingIgnoreCase("tom");
        assertNotNull(encontrados);
        assertEquals(2, encontrados.size());

        List<String> nombres = encontrados.stream().map(Perfume::getNombre).toList();
        assertTrue(nombres.contains("Tommy"));
        assertTrue(nombres.contains("Tom Ford Noir"));
    }

    @Test
    @DisplayName("Debería encontrar por marca conteniendo texto (case-insensitive)")
    void deberiaEncontrarPorMarcaContainingIgnoreCase() {
        List<Perfume> resUpper = perfumeRepository.findByMarcaContainingIgnoreCase("TOM");
        List<Perfume> resLower = perfumeRepository.findByMarcaContainingIgnoreCase("tom");
        List<Perfume> resMixed = perfumeRepository.findByMarcaContainingIgnoreCase("ToM");

        assertEquals(2, resUpper.size());
        assertEquals(2, resLower.size());
        assertEquals(2, resMixed.size());
    }

    @Test
    @DisplayName("Debería retornar lista vacía cuando no hay coincidencias")
    void deberiaRetornarListaVaciaCuandoNoHayCoincidencias() {
        List<Perfume> encontrados = perfumeRepository.findByNombreContainingIgnoreCase("inexistente");
        assertNotNull(encontrados);
        assertTrue(encontrados.isEmpty());
    }

    @Test
    @DisplayName("Debería buscar por precio en rango")
    void deberiaBuscarPorPrecioEntre() {
        // [200, 400] → Tom Ford Noir (350) y Acqua di Gio (250)
        List<Perfume> rango = perfumeRepository.findByPrecioBetween(200.0, 400.0);
        assertEquals(2, rango.size());

        List<String> nombres = rango.stream().map(Perfume::getNombre).toList();
        assertTrue(nombres.contains("Tom Ford Noir"));
        assertTrue(nombres.contains("Acqua di Gio"));
    }
}

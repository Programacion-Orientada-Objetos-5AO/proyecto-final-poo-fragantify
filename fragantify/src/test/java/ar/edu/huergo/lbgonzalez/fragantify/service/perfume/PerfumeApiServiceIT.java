package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceExternalDTO;

@SpringBootTest
class PerfumeApiServiceIT {

    @Autowired
    private PerfumeApiService perfumeApiService;

    @Test
    void buscarVersaceEros() {
        Map<String, String> filtros = new HashMap<>();
        filtros.put("brand", "Versace");
        filtros.put("name", "Eros");

        List<FragranceExternalDTO> res = perfumeApiService.buscarExternos(filtros);
        assertNotNull(res);
        System.out.println("RESULT_COUNT=" + res.size());
        for (int i = 0; i < Math.min(res.size(), 5); i++) {
            FragranceExternalDTO p = res.get(i);
            System.out.println("ITEM[" + i + "] name=" + p.getName()
                + ", brand=" + p.getBrand()
                + ", gender=" + p.getGender()
                + ", price=" + p.getPrice()
                + ", imageUrl=" + p.getImageUrl());
        }
    }
}


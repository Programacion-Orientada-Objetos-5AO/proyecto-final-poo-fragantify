package ar.edu.huergo.lbgonzalez.fragantify.controller.marcas;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.lbgonzalez.fragantify.dto.marcas.MostrarMarcaDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.marca.Marca;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.marcas.MarcasMapper;
import ar.edu.huergo.lbgonzalez.fragantify.service.marcas.MarcasService;

@RestController
@RequestMapping("/api/marcas")

public class MarcasController {

    @Autowired
    private MarcasService marcasService;


    @Autowired
    private MarcasMapper marcasMapper;

    @GetMapping
    public ResponseEntity<List<MostrarMarcaDTO>> listar() {
        List<Marca> marcas = marcasService.getMarcas();
        return ResponseEntity.ok(marcasMapper.toDTOList(marcas));
    }



    
}

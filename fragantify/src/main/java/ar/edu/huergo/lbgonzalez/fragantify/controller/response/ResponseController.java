package ar.edu.huergo.lbgonzalez.fragantify.controller.response;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.lbgonzalez.fragantify.dto.request.RequestDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.response.ResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.request.Request;
import ar.edu.huergo.lbgonzalez.fragantify.entity.response.Response;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.request.ResponseMapper;
import ar.edu.huergo.lbgonzalez.fragantify.repository.response.ResponseRepository;
import ar.edu.huergo.lbgonzalez.fragantify.service.request.ResponseRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/response")

public class ResponseController {

    @Autowired private ResponseRepository responseRepository;

    @Autowired private ResponseMapper ResponseMapper;


    @GetMapping
    public ResponseEntity<List<ResponsetDTO>> obtenerTodosLosResponse() {
        List<Response> requests = ResponseRepository.obtenerTodosLosResponses();
        return ResponseEntity.ok(ResponseMapper.toDTOList(requests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDTO> obtenerResponsePorId(@RequestParam Long id) {
        Response response = ResponseRepository.obtenerResponsePorId(id);
        return ResponseEntity.ok(ResponseMapper.toDTO(request));
    
    }

    @PostMapping
    public ResponseEntity<RequestDTO> crearRequest(@Valid     @RequestBody RequestDTO requestDTO) {
        Response response = ResponseMapper.toEntity(requestDTO);
        Response nuevoResponse = ResponseRepository.creaResponse(response);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevoResponse.getId())
                .toUri();
            
        return ResponseEntity.created(location).body(ResponseMapper.toDTO(nuevoResponse));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> actualizarRequest(@PathVariable Long id,@Valid @ResponseBody ResponseDTO responseDTO) {
        Response responseDetalles = ResponseMapper.toEntity(responseDTO);
        Response responseActualizado = ResponseRepository.actualizarResponse(id, requestDetalles);
        return ResponseEntity.ok(ResponseMapper.toDTO(responseActualizado));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRequest(@PathVariable Long id) {
        ResponseRepository.eliminarResponse(id);
        return ResponseEntity.noContent().build();
    }
    
    
}

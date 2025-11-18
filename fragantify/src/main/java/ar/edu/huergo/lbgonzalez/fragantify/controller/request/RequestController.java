package ar.edu.huergo.lbgonzalez.fragantify.controller.request;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ar.edu.huergo.lbgonzalez.fragantify.dto.request.RequestDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.request.Request;
import ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.request.RequestMapper;
import ar.edu.huergo.lbgonzalez.fragantify.service.request.RequestService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/requests")

public class RequestController {



    @Autowired private RequestService requestService;

    @Autowired private RequestMapper requestMapper;


    @GetMapping
    public ResponseEntity<List<RequestDTO>> obtenerTodosLosRequests() {
        List<Request> requests = requestService.obtenerTodosLosRequests();
        return ResponseEntity.ok(requestMapper.toDTOList(requests));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestDTO> obtenerRequestPorId(@RequestParam Long id) {
        Request request = requestService.obtenerRequestPorId(id);
        return ResponseEntity.ok(requestMapper.toDTO(request));
    
    }

    @PostMapping
    public ResponseEntity<RequestDTO> crearRequest(@Valid     @RequestBody RequestDTO requestDTO) {
        Request request = requestMapper.toEntity(requestDTO);
        Request nuevoRequest = requestService.creaRequest(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(nuevoRequest.getId())
                .toUri();
            
        return ResponseEntity.created(location).body(requestMapper.toDTO(nuevoRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestDTO> actualizarRequest(@PathVariable Long id,@Valid @RequestBody RequestDTO requestDTO) {
        Request requestDetalles = requestMapper.toEntity(requestDTO);
        Request requestActualizado = requestService.actualizarRequest(id, requestDetalles);
        return ResponseEntity.ok(requestMapper.toDTO(requestActualizado));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRequest(@PathVariable Long id) {
        requestService.eliminarRequest(id);
        return ResponseEntity.noContent().build();
    }
    
    

    
















}

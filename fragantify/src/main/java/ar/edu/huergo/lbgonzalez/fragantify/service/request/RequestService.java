package ar.edu.huergo.lbgonzalez.fragantify.service.request;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.huergo.lbgonzalez.fragantify.entity.request.Request;

import ar.edu.huergo.lbgonzalez.fragantify.repository.request.RequestRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;



    public List<Request> obtenerTodosLosRequests() {
        return requestRepository.findAll();
    }
    
    public Request obtenerRequestPorId(Long id) {
        return requestRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Request no encontrado"));
    }

    public Request creaRequest(Request request) {
        return requestRepository.save(request);
    }

    public Request actualizarRequest(Long id, Request requestDetalles) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request no encontrado"));

        request.setTitulo(requestDetalles.getTitulo());
        request.setDescripcion(requestDetalles.getDescripcion());
        request.setCreador(requestDetalles.getCreador());

        return requestRepository.save(request);
    }


    public void eliminarRequest(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request no encontrado"));
        requestRepository.delete(request);
    }











}


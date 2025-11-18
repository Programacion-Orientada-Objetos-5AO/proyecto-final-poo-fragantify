package ar.edu.huergo.lbgonzalez.fragantify.service.response;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.huergo.lbgonzalez.fragantify.entity.response.Response;
import ar.edu.huergo.lbgonzalez.fragantify.repository.response.ResponseRepository;
import jakarta.persistence.EntityNotFoundException;

public class ResponseService {
    @Autowired
    private ResponseRepository responseRepository;

    public List<Response> obtenerTodosLosResponses() {
        return responseRepository.findAll();
    }

    public Response obtenerResponsePorId(Long id) {
        return responseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Response no encontrado"));
    }

    public Response creaResponse(Response response) {
        return responseRepository.save(response);
    }

    public Response actualizarRequest(Long id, Response responseDetalles) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request no encontrado"));

        response.setTitulo(responseDetalles.getTitulo());
        response.setDescripcion(responseDetalles.getDescripcion());
        response.setCreador(responseDetalles.getCreador());

        return responseRepository.save(response);
    }


    public void eliminarRequest(Long id) {
        Response response = responseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Request no encontrado"));
        responseRepository.delete(response);
    }





    
}

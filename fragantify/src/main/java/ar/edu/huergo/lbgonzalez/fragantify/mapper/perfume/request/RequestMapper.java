package ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume.request;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.huergo.lbgonzalez.fragantify.dto.request.RequestDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.request.Request;

public class RequestMapper {
    public RequestDTO toDTO(Request request) {
        if (request == null) {
            return null;
        }
        RequestDTO dto = new RequestDTO();
        request.setId(request.getId());
        request.setTitulo(request.getTitulo());
        request.setDescripcion(request.getDescripcion());
        request.setCreador(request.getCreador());
        return request;
    }
}
       
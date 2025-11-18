package ar.edu.huergo.lbgonzalez.fragantify.repository.response;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.response.Response;


public interface ResponseRepository  extends JpaRepository<Response, Long> {

    
}
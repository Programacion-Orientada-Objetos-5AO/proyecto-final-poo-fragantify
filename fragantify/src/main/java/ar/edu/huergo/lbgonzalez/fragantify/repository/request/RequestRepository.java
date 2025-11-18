package ar.edu.huergo.lbgonzalez.fragantify.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.huergo.lbgonzalez.fragantify.entity.request.Request;

public interface RequestRepository  extends JpaRepository<Request, Long> {

    
}
package ar.edu.huergo.lbgonzalez.fragantify.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "service", "Fragantify API",
                "status", "ok",
                "endpoints", "/api/*"
        ));
    }
}

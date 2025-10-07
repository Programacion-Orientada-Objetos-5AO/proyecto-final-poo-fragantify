package ar.edu.huergo.lbgonzalez.fragantify.controller.Fragance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.huergo.lbgonzalez.fragantify.entity.fragance.Fragance;
import ar.edu.huergo.lbgonzalez.fragantify.service.api.FraganceService;

@RestController
@RequestMapping("/api/fragancias")
public class FraganceController {

    @Autowired
    private FraganceService fraganceService;

  @GetMapping
    public List<Fragance> buscarFragancias(@RequestParam Map<String, String> filtros) {
        return fraganceService.buscarFragancias(filtros);
    }
}
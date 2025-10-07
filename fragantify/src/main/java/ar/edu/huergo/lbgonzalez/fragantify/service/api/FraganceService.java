package ar.edu.huergo.lbgonzalez.fragantify.service.api;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import ar.edu.huergo.lbgonzalez.fragantify.entity.fragance.Fragance;
import reactor.core.publisher.Mono;

@Service
public class FraganceService {

    private static final String API_KEY = "949e184c758ac22dee7e737764f7aea4719ad8d5485b97a6499bf66eec28e99d";

    @Autowired
    private WebClient webClient;

    public List<Fragance> buscarFragancias(String query) {
        Mono<Fragance[]> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/fragrances")
                        .queryParam("search", query)
                        .build())
                .header("x-api-key", API_KEY)
                .retrieve()
                .bodyToMono(Fragance[].class);

        return Arrays.asList(response.block()); // bloquea para respuesta síncrona y convierte array a lista
    }
}

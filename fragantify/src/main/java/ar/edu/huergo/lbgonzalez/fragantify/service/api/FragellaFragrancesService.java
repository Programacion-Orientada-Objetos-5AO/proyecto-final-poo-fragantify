package ar.edu.huergo.lbgonzalez.fragantify.service.api;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.List;

@Service
public class FragellaFragrancesService {

    private final HttpClient http;
    private final ObjectMapper mapper;
    private final String baseUrl;
    private final String fragrancesPath;
    private final String apiKeyHeader;
    private final String apiKey;
    private final int timeoutSeconds;

    public FragellaFragrancesService(
            @Value("${fragella.api.base-url}") String baseUrl,
            @Value("${fragella.api.fragrances-path:/fragrances}") String fragrancesPath,
            @Value("${fragella.api.key-header:x-api-key}") String apiKeyHeader,
            @Value("${fragella.api.key:}") String apiKey,
            @Value("${fragella.api.timeout-seconds:10}") int timeoutSeconds,
            ObjectMapper mapper
    ) {
        // Normalizamos base y path
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.fragrancesPath = fragrancesPath.startsWith("/") ? fragrancesPath : ("/" + fragrancesPath);
        this.apiKeyHeader = apiKeyHeader;
        this.apiKey = apiKey;
        this.timeoutSeconds = timeoutSeconds;
        this.mapper = mapper;
        this.http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(timeoutSeconds))
                .build();
    }

    public List<FragranceDTO> searchFragrances(String search, Integer limit) {
        // Validaciones de entrada
        if (!StringUtils.hasText(search) || search.trim().length() < 3) {
            throw new IllegalArgumentException("El parámetro 'search' es requerido y debe tener al menos 3 caracteres.");
        }
        final int lim = (limit == null) ? 10 : Math.min(Math.max(limit, 1), 20); // default 10, máx 20

        if (!StringUtils.hasText(apiKey)) {
            throw new IllegalStateException("FRAGELLA_API_KEY no configurada (fragella.api.key).");
        }

        // Construimos la URI usando las props configuradas (NO hardcodear)
        URI uri = UriComponentsBuilder
                .fromUriString(baseUrl + fragrancesPath)
                .queryParam("search", search.trim())  // ¡hay que pasar valor!
                .queryParam("limit", lim)
                .build(true)
                .toUri();

        HttpRequest req = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .header(apiKeyHeader, apiKey) // header y valor desde propiedades
                .GET()
                .build();

        try {
            HttpResponse<String> res = http.send(req, HttpResponse.BodyHandlers.ofString());

            if (res.statusCode() < 200 || res.statusCode() >= 300) {
                // Propagamos el cuerpo de error para diagnosticar (401/403/404/5xx)
                throw new RuntimeException("Fragella /fragrances devolvió " + res.statusCode() + ": " + res.body());
            }

            // Si tu FragranceDTO mapea 1:1 con la respuesta, esto sirve.
            // Si la API usa claves tipo "Name","Brand","Image URL", deberías mapear a mano.
            return mapper.readValue(res.body(), new TypeReference<List<FragranceDTO>>() {});
        } catch (HttpTimeoutException e) {
            throw new RuntimeException("Timeout consultando Fragella /fragrances", e);
        } catch (Exception e) {
            throw new RuntimeException("Error consultando Fragella /fragrances", e);
        }
    }
}

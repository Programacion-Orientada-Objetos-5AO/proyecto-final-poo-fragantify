package ar.edu.huergo.lbgonzalez.fragantify.service.perfume;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.PerfumeExternoDTO;
import ar.edu.huergo.lbgonzalez.fragantify.exception.FragellaApiException;

/**
 * Cliente HTTP muy simple para consultar la API abierta de Fragella.
 */
@Service
public class FragellaPerfumeClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final URI perfumesEndpoint;
    private final String apiKeyHeader;
    private final String apiKeyValue;
    private final String searchParamName;
    private final String limitParamName;
    private final Integer defaultLimit;

    public FragellaPerfumeClient(
            @Value("${fragella.api.base-url:https://api.fragella.com}") String baseUrl,
            @Value("${fragella.api.perfumes-path:/perfumes}") String perfumesPath,
            @Value("${fragella.api.key-header:X-API-Key}") String apiKeyHeader,
            @Value("${fragella.api.key:}") String apiKeyValue,
            @Value("${fragella.api.search-param:search}") String searchParamName,
            @Value("${fragella.api.limit-param:limit}") String limitParamName,
            @Value("${fragella.api.default-limit:20}") Integer defaultLimit,
            ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .build();
        this.perfumesEndpoint = buildPerfumesEndpoint(baseUrl, perfumesPath);
        this.apiKeyHeader = apiKeyHeader;
        this.apiKeyValue = Optional.ofNullable(apiKeyValue).orElse("");
        this.searchParamName = searchParamName;
        this.limitParamName = limitParamName;
        this.defaultLimit = defaultLimit;
    }

    private URI buildPerfumesEndpoint(String baseUrl, String perfumesPath) {
        String normalizedBase = Optional.ofNullable(baseUrl)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElse("https://api.fragella.com");

        if (!normalizedBase.endsWith("/")) {
            normalizedBase = normalizedBase + "/";
        }

        String normalizedPath = Optional.ofNullable(perfumesPath)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .orElse("perfumes");

        if (normalizedPath.startsWith("/")) {
            normalizedPath = normalizedPath.substring(1);
        }

        return URI.create(normalizedBase + normalizedPath);
    }

    /**
     * Obtiene perfumes directamente desde la API externa.
     *
     * @param query Texto de búsqueda libre (marca, notas, etc.).
     * @param limit Cantidad máxima de registros deseada.
     */
    public List<PerfumeExternoDTO> obtenerPerfumes(String query, Integer limit) {
        URI uri = construirUri(query, limit);
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(uri)
                .timeout(Duration.ofSeconds(10))
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .GET();

        if (StringUtils.hasText(apiKeyValue)) {
            requestBuilder.header(apiKeyHeader, apiKeyValue);
        }

        HttpRequest request = requestBuilder.build();
        HttpResponse<String> response;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new FragellaApiException("La consulta a la API de Fragella fue interrumpida", e);
        } catch (HttpTimeoutException e) {
            throw new FragellaApiException("La API de Fragella tardó demasiado en responder", e);
        } catch (IOException e) {
            throw new FragellaApiException("No se pudo contactar a la API de Fragella", e);
        }

        if (response.statusCode() >= 400) {
            throw new FragellaApiException("La API de Fragella devolvió un estado inesperado: " + response.statusCode());
        }

        return parsearPerfumes(response.body());
    }

    private URI construirUri(String query, Integer limit) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUri(perfumesEndpoint);

        if (StringUtils.hasText(query)) {
            builder.queryParam(searchParamName, query);
        }

        Integer finalLimit = Optional.ofNullable(limit)
                .filter(value -> value != null && value > 0)
                .orElse(defaultLimit);

        if (finalLimit != null && finalLimit > 0 && StringUtils.hasText(limitParamName)) {
            builder.queryParam(limitParamName, finalLimit);
        }

        return builder.build(true).toUri();
    }

    private List<PerfumeExternoDTO> parsearPerfumes(String body) {
        try {
            JsonNode root = objectMapper.readTree(body);
            JsonNode perfumesNode = localizarArregloDePerfumes(root);
            List<PerfumeExternoDTO> perfumes = new ArrayList<>();
            for (JsonNode perfumeNode : perfumesNode) {
                perfumes.add(convertirPerfume(perfumeNode));
            }
            return perfumes;
        } catch (JsonProcessingException e) {
            throw new FragellaApiException("No se pudo interpretar la respuesta de la API de Fragella", e);
        }
    }

    private JsonNode localizarArregloDePerfumes(JsonNode root) {
        if (root == null || root.isMissingNode() || root.isNull()) {
            throw new FragellaApiException("La API de Fragella devolvió una respuesta vacía");
        }

        if (root.isArray()) {
            return root;
        }

        List<String> posiblesCampos = List.of("data", "results", "perfumes", "items", "response");
        for (String campo : posiblesCampos) {
            JsonNode node = root.path(campo);
            if (node.isArray()) {
                return node;
            }
            if (node.isObject()) {
                JsonNode nestedArray = buscarArrayRecursivo(node, posiblesCampos);
                if (nestedArray != null) {
                    return nestedArray;
                }
            }
        }

        throw new FragellaApiException("No se encontró una lista de perfumes en la respuesta de la API externa");
    }

    private JsonNode buscarArrayRecursivo(JsonNode node, List<String> posiblesCampos) {
        for (String campo : posiblesCampos) {
            JsonNode nested = node.path(campo);
            if (nested.isArray()) {
                return nested;
            }
            if (nested.isObject()) {
                JsonNode deeper = buscarArrayRecursivo(nested, posiblesCampos);
                if (deeper != null) {
                    return deeper;
                }
            }
        }
        Iterator<String> fieldNames = node.fieldNames();
        while (fieldNames.hasNext()) {
            JsonNode nested = node.path(fieldNames.next());
            if (nested.isArray()) {
                return nested;
            }
            if (nested.isObject()) {
                JsonNode deeper = buscarArrayRecursivo(nested, posiblesCampos);
                if (deeper != null) {
                    return deeper;
                }
            }
        }
        return null;
    }

    private PerfumeExternoDTO convertirPerfume(JsonNode perfumeNode) {
        return PerfumeExternoDTO.builder()
                .idExterno(extraerTexto(perfumeNode, "id", "idPerfume", "perfumeId", "slug"))
                .nombre(extraerTexto(perfumeNode, "nombre", "name", "perfume", "title"))
                .marca(extraerMarca(perfumeNode))
                .precio(extraerPrecio(perfumeNode))
                .familiaOlfativa(extraerTexto(perfumeNode, "familiaOlfativa", "familia_olfativa", "olfactive_family", "family"))
                .descripcion(extraerTexto(perfumeNode, "descripcion", "description", "notas", "notes"))
                .build();
    }

    private String extraerMarca(JsonNode perfumeNode) {
        String marcaDirecta = extraerTexto(perfumeNode, "marca", "brand", "house", "brand_name");
        if (StringUtils.hasText(marcaDirecta)) {
            return marcaDirecta;
        }
        JsonNode brandNode = perfumeNode.path("brand");
        if (brandNode.isObject()) {
            String marca = extraerTexto(brandNode, "nombre", "name", "title");
            if (StringUtils.hasText(marca)) {
                return marca;
            }
        }
        return null;
    }

    private Double extraerPrecio(JsonNode perfumeNode) {
        JsonNode priceNode = localizarNodoPrecio(perfumeNode);
        if (priceNode == null || priceNode.isMissingNode() || priceNode.isNull()) {
            return null;
        }

        if (priceNode.isNumber()) {
            return priceNode.asDouble();
        }

        if (priceNode.isTextual()) {
            String texto = priceNode.asText().replaceAll("[^0-9,.-]", "");
            if (!StringUtils.hasText(texto)) {
                return null;
            }
            texto = texto.replace(',', '.');
            try {
                return Double.parseDouble(texto);
            } catch (NumberFormatException ex) {
                return null;
            }
        }
        return null;
    }

    private JsonNode localizarNodoPrecio(JsonNode perfumeNode) {
        List<String> posiblesPrecios = List.of("precio", "price", "precio_promedio", "precioPromedio", "price_value", "priceValue");
        for (String campo : posiblesPrecios) {
            JsonNode node = perfumeNode.path(campo);
            if (!node.isMissingNode() && !node.isNull()) {
                return node;
            }
        }
        // Algunos endpoints devuelven un objeto price { min, max, value }
        JsonNode priceNode = perfumeNode.path("price");
        if (priceNode.isObject()) {
            for (String campo : List.of("value", "promedio", "average", "min")) {
                JsonNode nested = priceNode.path(campo);
                if (!nested.isMissingNode() && !nested.isNull()) {
                    return nested;
                }
            }
        }
        return null;
    }

    private String extraerTexto(JsonNode node, String... posiblesCampos) {
        for (String campo : posiblesCampos) {
            JsonNode valor = node.path(campo);
            if (valor.isTextual()) {
                String texto = valor.asText();
                if (StringUtils.hasText(texto)) {
                    return texto;
                }
            } else if (valor.isNumber()) {
                return valor.asText();
            }
        }
        return null;
    }
}

package ar.edu.huergo.lbgonzalez.fragantify.repository.perfume;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class FragranceJsonRepository {

    private final ObjectMapper objectMapper;

    @Value("${fragrances.json.store-path:data/fragrances.json}")
    private String storePath;

    public List<FragranceDTO> loadAll() {
        try {
            Path path = resolvePath();
            if (!Files.exists(path)) {
                return Collections.emptyList();
            }
            byte[] bytes = Files.readAllBytes(path);
            if (bytes.length == 0) {
                return Collections.emptyList();
            }
            return objectMapper.readValue(bytes, new TypeReference<List<FragranceDTO>>() {});
        } catch (IOException ex) {
            log.warn("No se pudo leer el JSON de fragancias: {}", ex.getMessage());
            return Collections.emptyList();
        }
    }

    public void replaceAll(List<FragranceDTO> items) {
        if (items == null) {
            items = Collections.emptyList();
        }
        try {
            Path path = resolvePath();
            Files.createDirectories(path.getParent());
            // pretty print without changing global mapper config
            byte[] bytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(items);
            Files.write(path, bytes);
            log.info("Se escribieron {} fragancias en {}", items.size(), path.toString());
        } catch (IOException ex) {
            log.warn("No se pudo escribir el JSON de fragancias: {}", ex.getMessage());
        }
    }

    private Path resolvePath() {
        return Paths.get(storePath).toAbsolutePath().normalize();
    }
}
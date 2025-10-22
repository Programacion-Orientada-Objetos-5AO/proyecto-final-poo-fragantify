package ar.edu.huergo.lbgonzalez.fragantify.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import ar.edu.huergo.lbgonzalez.fragantify.repository.perfume.FragranceJsonRepository;
import ar.edu.huergo.lbgonzalez.fragantify.service.perfume.PerfumeApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FragranceSyncOnStart implements CommandLineRunner {

    private final PerfumeApiService perfumeApiService;
    private final FragranceJsonRepository jsonRepository;

    @Value("${fragrances.sync.on-start:true}")
    private boolean syncOnStart;

    @Value("${fragrances.sync.force-refresh:false}")
    private boolean forceRefresh;

    @Value("${fragrances.sync.bulk.enabled:true}")
    private boolean bulkEnabled;

    @Value("${fragrances.sync.bulk.strategy:page}")
    private String bulkStrategy;

    @Value("${fragrances.sync.bulk.max-pages:50}")
    private int bulkMaxPages;

    @Value("${fragrances.sync.bulk.page-size:100}")
    private int bulkPageSize;

    @Override
    public void run(String... args) {
        if (!syncOnStart) {
            log.debug("Fragrance sync on start is disabled.");
            return;
        }

        try {
            if (bulkEnabled) {
                log.info("Starting bulk sync on startup (strategy={}, maxPages={}, pageSize={}, forceRefresh={})",
                        bulkStrategy, bulkMaxPages, bulkPageSize, forceRefresh);
                int fetched = perfumeApiService.bulkSync(bulkStrategy, bulkMaxPages, bulkPageSize);
                log.info("Bulk sync fetched {} items. JSON merged.", fetched);
                return;
            } else {
                // Simple sync only runs if JSON is missing or forceRefresh=true
                List<FragranceDTO> existing = jsonRepository.loadAll();
                boolean hasExisting = existing != null && !existing.isEmpty();
                if (hasExisting && !forceRefresh) {
                    log.info("Fragrances JSON already present ({} items). Skipping simple API sync.", existing.size());
                    return;
                }
                log.info("Starting simple sync on startup (forceRefresh={}).", forceRefresh);
                List<FragranceDTO> fetched = perfumeApiService.buscarDetalle(Collections.emptyMap());
                if (fetched == null || fetched.isEmpty()) {
                    log.warn("No fragrances fetched from external API on startup.");
                } else {
                    log.info("Fetched {} fragrances from external API on startup.", fetched.size());
                }
            }
        } catch (Exception ex) {
            log.warn("Startup fragrance sync failed: {}", ex.getMessage());
        }
    }
}

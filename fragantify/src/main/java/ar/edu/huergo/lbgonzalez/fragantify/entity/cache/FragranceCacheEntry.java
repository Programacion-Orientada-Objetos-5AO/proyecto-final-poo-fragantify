package ar.edu.huergo.lbgonzalez.fragantify.entity.cache;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fragrance_cache")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FragranceCacheEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * JSON serializado con la respuesta completa de la API.
     */
    @Lob
    @Column(nullable = false)
    private String payload;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}

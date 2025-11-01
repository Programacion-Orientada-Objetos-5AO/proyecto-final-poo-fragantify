package ar.edu.huergo.lbgonzalez.fragantify.entity.perfume;

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
@Table(name = "external_fragrance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExternalFragrance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 160)
    private String externalId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 150)
    private String brand;

    @Column(length = 64)
    private String price;

    @Column(length = 512)
    private String imageUrl;

    @Column(length = 32)
    private String gender;

    private Integer longevity;
    private Integer sillage;

    @Lob
    private String generalNotesJson;

    @Lob
    private String mainAccordsJson;

    @Lob
    private String mainAccordsPercentageJson;

    @Lob
    private String rawPayload;

    @Column(nullable = false)
    private LocalDateTime syncedAt;
}

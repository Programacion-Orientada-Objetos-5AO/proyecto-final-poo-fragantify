package ar.edu.huergo.lbgonzalez.fragantify.dto.perfume;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record FragranceResponseDTO(
        String id,
        String name,
        String brand,
        String price,
        String imageUrl,
        List<String> imageFallbacks,
        String gender,
        String longevity,
        String sillage,
        List<String> generalNotes,
        List<String> mainAccords,
        Map<String, Double> mainAccordsPercentage,
        FragranceNotesDTO notes,
        String purchaseUrl,
        List<FragranceRankingItemDTO> seasonRanking,
        List<FragranceRankingItemDTO> occasionRanking) {

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record FragranceNotesDTO(
            List<FragranceNoteDTO> top,
            List<FragranceNoteDTO> middle,
            List<FragranceNoteDTO> base) {
    }

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record FragranceNoteDTO(String name, String imageUrl) {
    }

    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record FragranceRankingItemDTO(String name, Double score) {
    }
}

package ar.edu.huergo.lbgonzalez.fragantify.dto.perfume;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FragranceExternalDTO {
    private String name;
    private String brand;
    private String price;
    private String imageUrl;
    private List<String> imageFallbacks;
    private String gender;
    private String longevity;
    private String sillage;
    private List<String> generalNotes;
    private List<String> mainAccords;
    private Map<String, Double> mainAccordsPercentage;
    private NotesDTO notes;
    private String purchaseUrl;
    private List<RankingItemDTO> seasonRanking;
    private List<RankingItemDTO> occasionRanking;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NotesDTO {
        private List<NoteDTO> top;
        private List<NoteDTO> middle;
        private List<NoteDTO> base;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoteDTO {
        private String name;
        private String imageUrl;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RankingItemDTO {
        private String name;
        private Double score;
    }
}

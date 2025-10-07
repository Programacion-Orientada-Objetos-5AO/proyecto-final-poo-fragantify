package ar.edu.huergo.lbgonzalez.fragantify.entity.fragance;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fragance {
    
    @JsonProperty("Name")
    private String name;
    
    @JsonProperty("Brand")
    private String brand;

    @JsonProperty("Price")
    private String price;

    // Campo con espacio en el nombre:
    @JsonProperty("Image URL")
    private String imageUrl;

    @JsonProperty("Gender")
    private String gender;

    @JsonProperty("Longevity")
    private Integer longevity; // porcentaje (0-100)

    @JsonProperty("Sillage")
    private Integer sillage;   // porcentaje (0-100)

    @JsonProperty("General Notes")
    private List<String> generalNotes;

    @JsonProperty("Main Accords")
    private List<String> mainAccords;

    @JsonProperty("Main Accords Percentage")
    private Map<String, Double> mainAccordsPercentage;

    @JsonProperty("Notes")
    private Notes notes; // Top / Middle / Base

    // Opcionales
    @JsonProperty("Image Fallbacks")
    private List<String> imageFallbacks;

    @JsonProperty("Purchase URL")
    private String purchaseUrl;

    @JsonProperty("Season Ranking")
    private List<RankingItem> seasonRanking;

    @JsonProperty("Occasion Ranking")
    private List<RankingItem> occasionRanking;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Notes {
        @JsonProperty("Top")
        private List<NoteItem> top;

        @JsonProperty("Middle")
        private List<NoteItem> middle;

        @JsonProperty("Base")
        private List<NoteItem> base;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NoteItem {
        @JsonProperty("name")
        private String name;

        @JsonProperty("imageUrl")
        private String imageUrl;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RankingItem {
        private String name;
        private Double score;
    }
}
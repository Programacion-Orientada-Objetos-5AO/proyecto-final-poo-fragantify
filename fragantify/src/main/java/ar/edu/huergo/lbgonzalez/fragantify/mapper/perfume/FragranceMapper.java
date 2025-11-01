package ar.edu.huergo.lbgonzalez.fragantify.mapper.perfume;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceResponseDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceResponseDTO.FragranceNoteDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceResponseDTO.FragranceNotesDTO;
import ar.edu.huergo.lbgonzalez.fragantify.dto.perfume.FragranceResponseDTO.FragranceRankingItemDTO;
import ar.edu.huergo.lbgonzalez.fragantify.entity.perfume.Perfume;

@Component
public class FragranceMapper {

    private static final DecimalFormat PRICE_FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        PRICE_FORMAT = new DecimalFormat("$###,##0.00", symbols);
    }

    public FragranceResponseDTO fromExternal(FragranceDTO source) {
        if (source == null) {
            return null;
        }

        return FragranceResponseDTO.builder()
                .name(source.getName())
                .brand(source.getBrand())
                .price(source.getPrice())
                .imageUrl(source.getImageUrl())
                .imageFallbacks(source.getImageFallbacks())
                .gender(source.getGender())
                .longevity(formatPercentage(source.getLongevity()))
                .sillage(formatPercentage(source.getSillage()))
                .generalNotes(normalizeList(source.getGeneralNotes()))
                .mainAccords(normalizeList(source.getMainAccords()))
                .mainAccordsPercentage(source.getMainAccordsPercentage())
                .notes(mapNotes(source.getNotes()))
                .purchaseUrl(source.getPurchaseUrl())
                .seasonRanking(mapRanking(source.getSeasonRanking()))
                .occasionRanking(mapRanking(source.getOccasionRanking()))
                .build();
    }

    public FragranceResponseDTO fromEntity(Perfume perfume) {
        if (perfume == null) {
            return null;
        }

        List<String> accords = splitCommaSeparated(perfume.getFamiliaOlfativa());
        String imageUrl = buildImagePath(perfume);

        return FragranceResponseDTO.builder()
                .id(perfume.getId() != null ? perfume.getId().toString() : null)
                .name(perfume.getNombre())
                .brand(perfume.getMarca())
                .price(PRICE_FORMAT.format(perfume.getPrecio()))
                .imageUrl(imageUrl)
                .gender("Unisex")
                .generalNotes(accords.isEmpty() ? null : accords)
                .mainAccords(accords.isEmpty() ? null : accords)
                .build();
    }

    private FragranceNotesDTO mapNotes(FragranceDTO.Notes notes) {
        if (notes == null) {
            return null;
        }
        List<FragranceNoteDTO> top = mapNoteItems(notes.getTop());
        List<FragranceNoteDTO> middle = mapNoteItems(notes.getMiddle());
        List<FragranceNoteDTO> base = mapNoteItems(notes.getBase());

        if ((top == null || top.isEmpty()) && (middle == null || middle.isEmpty()) && (base == null || base.isEmpty())) {
            return null;
        }

        return FragranceNotesDTO.builder()
                .top(top)
                .middle(middle)
                .base(base)
                .build();
    }

    private List<FragranceNoteDTO> mapNoteItems(List<FragranceDTO.NoteItem> items) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        return items.stream()
                .filter(Objects::nonNull)
                .map(item -> FragranceNoteDTO.builder()
                        .name(item.getName())
                        .imageUrl(item.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    private List<FragranceRankingItemDTO> mapRanking(List<FragranceDTO.RankingItem> ranking) {
        if (ranking == null || ranking.isEmpty()) {
            return Collections.emptyList();
        }
        return ranking.stream()
                .filter(Objects::nonNull)
                .map(item -> FragranceRankingItemDTO.builder()
                        .name(item.getName())
                        .score(item.getScore())
                        .build())
                .collect(Collectors.toList());
    }

    private String formatPercentage(Integer value) {
        if (value == null) {
            return null;
        }
        return value + "%";
    }

    private List<String> normalizeList(List<String> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        List<String> normalized = values.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toList());
        return normalized.isEmpty() ? null : normalized;
    }

    private List<String> splitCommaSeparated(String value) {
        if (value == null || value.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toList());
    }

    private String buildImagePath(Perfume perfume) {
        if (perfume.getNombre() == null && perfume.getMarca() == null) {
            return null;
        }
        String slug = (perfume.getMarca() + "-" + perfume.getNombre())
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");
        if (slug.isBlank()) {
            return null;
        }
        return "/images/perfumes/" + slug + ".jpg";
    }
}

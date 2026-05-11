package ir.lobox.imdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestMovieByYear {
    int year;
    String bestTitle;
    double rating;
    long votes;
    String genres;

    public List<String> getGenresAsList() {
        if (genres == null || genres.isBlank()) {
            return List.of();
        }
        return Arrays.stream(genres.split(","))
                .map(String::trim)
                .toList();
    }
}


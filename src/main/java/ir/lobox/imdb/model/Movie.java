package ir.lobox.imdb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Movie {
    private String tconst;
    private String actorId;
    private String titleType;
    private String actorName;
    private String primaryTitle;
    private Integer startYear;
    private String category;
    private String characters;
}

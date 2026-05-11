package ir.lobox.imdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TitleCrew {
    private String tconst;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private String startYear;
    private String person_id;
    private String primaryName;
    private String birthYear;
}

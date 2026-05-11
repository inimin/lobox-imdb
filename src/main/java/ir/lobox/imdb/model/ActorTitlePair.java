package ir.lobox.imdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActorTitlePair {
    private String tconst;
    private String actorId;
    private String titleType;
    private String actorName;
    private String primaryTitle;
    private Integer startYear;
    private String category;
    private String characters;
}

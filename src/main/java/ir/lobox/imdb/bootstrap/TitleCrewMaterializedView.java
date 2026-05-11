package ir.lobox.imdb.bootstrap;

import ir.lobox.imdb.config.ImdbProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TitleCrewMaterializedView {
    private static final Logger log = LoggerFactory.getLogger(TitleCrewMaterializedView.class);

    private final ImdbProperties props;
    private final JdbcTemplate jdbcTemplate;

    @Order(4)
    @EventListener(ApplicationReadyEvent.class)
    public void initializeViews() {
        String sql = """
                CREATE TABLE IF NOT EXISTS title_same_director_writer_alive AS
                WITH same_person_titles AS (
                    SELECT
                        tc.tconst,
                        d.person_id
                    FROM "title.crew" tc
                    CROSS JOIN UNNEST(STRING_SPLIT(tc.directors, ',')) AS d(person_id)
                    WHERE tc.directors IS NOT NULL
                      AND tc.writers IS NOT NULL
                      AND tc.directors <> '\\N'
                      AND tc.writers <> '\\N'
                      AND strpos(',' || tc.writers || ',', ',' || d.person_id || ',') > 0
                )
                SELECT DISTINCT
                    tb.tconst,
                    tb.titleType,
                    tb.primaryTitle,
                    tb.originalTitle,
                    tb.startYear,
                    s.person_id,
                    nb.primaryName,
                    nb.birthYear
                FROM same_person_titles s
                JOIN "name.basics" nb
                    ON nb.nconst = s.person_id
                JOIN "title.basics" tb
                    ON tb.tconst = s.tconst
                WHERE nb.deathYear = '\\N'
                  AND tb.startYear IS NOT NULL
                ORDER BY tb.startYear DESC, tb.primaryTitle;
                """;
        jdbcTemplate.execute(sql);
    }
}

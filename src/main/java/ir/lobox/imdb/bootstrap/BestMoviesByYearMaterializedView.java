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
public class BestMoviesByYearMaterializedView {
    private static final Logger log = LoggerFactory.getLogger(BestMoviesByYearMaterializedView.class);

    private final ImdbProperties props;
    private final JdbcTemplate jdbcTemplate;

    @Order(6)
    @EventListener(ApplicationReadyEvent.class)
    public void initializeViews() {
        String sql = """
                CREATE TABLE IF NOT EXISTS best_movies_by_year AS
                WITH movies AS (
                    SELECT
                        tb.tconst,
                        tb.primaryTitle,
                        tb.startYear,
                        tb.genres,
                        tr.averageRating,
                        tr.numVotes,
                        ROW_NUMBER() OVER (
                            PARTITION BY tb.startYear, tb.genres
                            ORDER BY tr.averageRating DESC, tr.numVotes DESC
                        ) AS rn
                    FROM "title.basics" tb
                    JOIN "title.ratings" tr ON tb.tconst = tr.tconst
                    WHERE tb.titleType = 'movie'
                      AND tb.startYear IS NOT NULL
                      AND tr.numVotes >= 5000
                      AND tb.isAdult = 0
                )
                SELECT
                    startYear AS year,
                    primaryTitle AS best_title,
                    averageRating AS rating,
                    numVotes AS votes,
                    genres
                FROM movies
                WHERE rn = 1;
                """;
        jdbcTemplate.execute(sql);
    }
}

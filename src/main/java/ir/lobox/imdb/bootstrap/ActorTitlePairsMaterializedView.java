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
public class ActorTitlePairsMaterializedView {

    private static final Logger log = LoggerFactory.getLogger(ActorTitlePairsMaterializedView.class);

    private final ImdbProperties props;
    private final JdbcTemplate jdbcTemplate;

    @Order(5)
    @EventListener(ApplicationReadyEvent.class)
    public void initializeViews() {
        String sql = """
                CREATE TABLE IF NOT EXISTS actor_title_pairs AS
                SELECT
                    p.tconst,
                    p.nconst AS actor_id,
                    nb.primaryName AS actor_name,
                    tb.titleType,
                    tb.primaryTitle,
                    tb.startYear,
                    p.category,
                    p.characters
                FROM "title.principals" p
                JOIN "title.basics" tb
                    ON tb.tconst = p.tconst
                JOIN "name.basics" nb
                    ON nb.nconst = p.nconst
                WHERE p.category IN ('actor', 'actress')
                  AND tb.startYear IS NOT NULL
                  AND tb.titleType IN ('movie', 'tvMovie', 'tvSeries', 'tvMiniSeries', 'short');
                
                CREATE INDEX IF NOT EXISTS idx_actor_title_pairs_actor
                    ON actor_title_pairs(actor_id);
                
                CREATE INDEX IF NOT EXISTS idx_actor_title_pairs_title
                    ON actor_title_pairs(tconst);
                """;
        jdbcTemplate.execute(sql);
    }
}

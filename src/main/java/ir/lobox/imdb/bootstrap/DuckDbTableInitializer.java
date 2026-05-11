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
public class DuckDbTableInitializer {
    private static final Logger log = LoggerFactory.getLogger(DuckDbTableInitializer.class);

    private final JdbcTemplate jdbcTemplate;
    private final ImdbProperties props;

    @Order(3)
    @EventListener(ApplicationReadyEvent.class)
    public void initializeViews() {
        for(String tableName : props.getTableNames()) {
            String path = props.getTargetPath(tableName);
            String sql = String.format("""
                CREATE TABLE IF NOT EXISTS "%s" AS
                SELECT * FROM read_parquet('%s')
                """, tableName, path);

            log.info("Creating table {} in DuckDB", tableName);
            jdbcTemplate.execute(sql);
        }
    }
}

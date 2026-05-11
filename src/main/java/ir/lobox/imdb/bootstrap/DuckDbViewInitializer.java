package ir.lobox.imdb.bootstrap;

import ir.lobox.imdb.config.ImdbProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(3)
public class DuckDbViewInitializer {
    private final JdbcTemplate jdbcTemplate;
    private final ImdbProperties props;
    @EventListener(ApplicationReadyEvent.class)
    public void initializeViews() {
        for(String tableName : props.getTableNames()) {
            String path = props.getTargetPath(tableName);
            String sql = String.format("""
                CREATE VIEW IF NOT EXISTS "%s" AS
                SELECT * FROM read_parquet('%s')
                """, tableName, path);

            jdbcTemplate.execute(sql);
        }
    }
}

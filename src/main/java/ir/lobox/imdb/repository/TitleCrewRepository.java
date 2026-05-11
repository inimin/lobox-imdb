package ir.lobox.imdb.repository;

import ir.lobox.imdb.bootstrap.ImdbParquetInitializer;
import ir.lobox.imdb.model.PageResult;
import ir.lobox.imdb.model.TitleCrew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TitleCrewRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(TitleCrewRepository.class);

    public TitleCrewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void findPaged() {
        String sql = """
                    SELECT * FROM title_same_director_writer_alive
        """;

        List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> row : result) {
            log.info(row.toString());
        }
    }
}

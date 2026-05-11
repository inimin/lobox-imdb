package ir.lobox.imdb.repository;

import ir.lobox.imdb.dto.PageResponse;
import ir.lobox.imdb.model.ActorTitlePair;
import ir.lobox.imdb.model.BestMovieByYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorPairsRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(ActorPairsRepository.class);

    public ActorPairsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PageResponse<ActorTitlePair> findActorPairs(String actor1, String actor2, int page, int size) {
        int validPage = Math.max(page, 0);
        int validSize = Math.min(Math.max(size, 1), 100);
        int offset = validPage * validSize;

        String sql = """
                SELECT DISTINCT t1.*
                FROM actor_title_pairs t1
                JOIN actor_title_pairs t2
                  ON t1.tconst = t2.tconst
                WHERE t1.actor_name = ?
                  AND t2.actor_name = ?
                ORDER BY t1.startYear DESC
                LIMIT ? OFFSET ?;
                """;
        RowMapper<ActorTitlePair> rowMapper = BeanPropertyRowMapper.newInstance(ActorTitlePair.class);
        List<ActorTitlePair> content = jdbcTemplate.query(sql, rowMapper, actor1, actor2, validSize, offset);

        String countSql = """
                SELECT DISTINCT COUNT(t1.*)
                FROM actor_title_pairs t1
                JOIN actor_title_pairs t2
                  ON t1.tconst = t2.tconst
                WHERE t1.actor_name = ?
                  AND t2.actor_name = ?;
                """;
        Integer totalElements = jdbcTemplate.queryForObject(countSql, Integer.class, actor1, actor2);
        int total = totalElements != null ? totalElements : 0;
        int totalPages = (total == 0) ? 0 : (int) Math.ceil((double) total / validSize);

        return PageResponse.<ActorTitlePair>builder()
                .content(content)
                .page(validPage)
                .size(validSize)
                .totalElements(total)
                .totalPages(totalPages)
                .first(validPage == 0)
                .last(validPage >= totalPages - 1)
                .build();
    }

}

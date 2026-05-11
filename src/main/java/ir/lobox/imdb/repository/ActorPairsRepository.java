package ir.lobox.imdb.repository;

import ir.lobox.imdb.model.ActorTitlePair;
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

    public List<ActorTitlePair> findActorPairs(String actor1, String actor2) {
        String sql = """
                SELECT DISTINCT t1.*
                FROM actor_title_pairs t1
                JOIN actor_title_pairs t2
                  ON t1.tconst = t2.tconst
                WHERE t1.actor_name = ?
                  AND t2.actor_name = ?
                ORDER BY t1.startYear DESC;
                """;

        RowMapper<ActorTitlePair> rowMapper = BeanPropertyRowMapper.newInstance(ActorTitlePair.class);
        jdbcTemplate.queryForList(sql, actor1, actor2);
        return jdbcTemplate.query(sql, rowMapper, actor1, actor2);
    }

}

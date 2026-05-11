package ir.lobox.imdb.repository;

import ir.lobox.imdb.model.BestMovieByYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BestMoviesByYearRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(BestMoviesByYearRepository.class);

    public BestMoviesByYearRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BestMovieByYear> findBestMoviesByYear(String genre) {
        String sql = """
                SELECT *
                FROM best_movies_by_year
                WHERE genres LIKE ?
                ORDER BY year DESC;
        """;
        RowMapper<BestMovieByYear> rowMapper = BeanPropertyRowMapper.newInstance(BestMovieByYear.class);
        return  jdbcTemplate.query(sql,rowMapper, "%" + genre + "%");
    }
}

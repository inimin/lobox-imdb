package ir.lobox.imdb.repository;

import ir.lobox.imdb.dto.PageResponse;
import ir.lobox.imdb.model.BestMovieByYear;
import ir.lobox.imdb.model.TitleCrew;
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

    public PageResponse<BestMovieByYear> findBestMoviesByYear(String genre, int page, int size) {
        int validPage = Math.max(page, 0);
        int validSize = Math.min(Math.max(size, 1), 100);
        int offset = validPage * validSize;


        String sql = """
                SELECT *
                FROM best_movies_by_year
                WHERE genres LIKE ?
                ORDER BY year DESC
                LIMIT ? OFFSET ?;
        """;
        RowMapper<BestMovieByYear> rowMapper = BeanPropertyRowMapper.newInstance(BestMovieByYear.class);
        List<BestMovieByYear> content =  jdbcTemplate.query(sql,rowMapper, "%" + genre + "%",validSize, offset);

        String countSql = """
                SELECT COUNT(*)
                FROM best_movies_by_year
                WHERE genres LIKE ?;
        """;
        Integer totalElements = jdbcTemplate.queryForObject(countSql, Integer.class, "%" + genre + "%");
        int total = totalElements != null ? totalElements : 0;
        int totalPages = (total == 0) ? 0 : (int) Math.ceil((double) total / validSize);

        return PageResponse.<BestMovieByYear>builder()
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

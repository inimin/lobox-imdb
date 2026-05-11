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
import java.util.Map;

@Repository
public class TitleCrewRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(TitleCrewRepository.class);

    public TitleCrewRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public PageResponse<TitleCrew> findPaged(int page, int size) {

        int validPage = Math.max(page, 0);
        int validSize = Math.min(Math.max(size, 1), 100);

        int offset = validPage * validSize;


        String sql = """
                    SELECT * FROM title_same_director_writer_alive LIMIT ? OFFSET ?;
        """;

        RowMapper<TitleCrew> rowMapper = BeanPropertyRowMapper.newInstance(TitleCrew.class);

        List<TitleCrew> content = jdbcTemplate.query(sql,rowMapper, validSize, offset);

        String countSql = """
                SELECT COUNT(*) 
                FROM title_same_director_writer_alive
                """;

        Integer totalElements = jdbcTemplate.queryForObject(countSql, Integer.class);
        int total = totalElements != null ? totalElements : 0;
        int totalPages = (total == 0) ? 0 : (int) Math.ceil((double) total / validSize);

        return PageResponse.<TitleCrew>builder()
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

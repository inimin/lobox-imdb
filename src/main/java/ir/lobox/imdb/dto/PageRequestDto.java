package ir.lobox.imdb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDto {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;

    private int page = DEFAULT_PAGE;
    private int size = DEFAULT_SIZE;
    private String sortBy = "id";           // default sort field
    private String sortDir = "desc";        // asc or desc

    public Pageable toPageable() {
        int validPage = Math.max(page, 0);
        int validSize = Math.min(Math.max(size, 1), MAX_SIZE);

        Sort.Direction direction = "asc".equalsIgnoreCase(sortDir)
                ? Sort.Direction.ASC : Sort.Direction.DESC;

        return PageRequest.of(validPage, validSize, Sort.by(direction, sortBy));
    }
}

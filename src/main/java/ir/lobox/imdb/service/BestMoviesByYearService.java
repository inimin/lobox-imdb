package ir.lobox.imdb.service;

import ir.lobox.imdb.dto.PageResponse;
import ir.lobox.imdb.model.BestMovieByYear;
import ir.lobox.imdb.repository.BestMoviesByYearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BestMoviesByYearService {

    private static final Logger log = LoggerFactory.getLogger(BestMoviesByYearService.class);
    private final BestMoviesByYearRepository bestMoviesByYearRepository;

    public BestMoviesByYearService(BestMoviesByYearRepository bestMoviesByYearRepository) {
        this.bestMoviesByYearRepository = bestMoviesByYearRepository;
    }

    public PageResponse<BestMovieByYear> getBestMoviesByYear(String genre, int page, int size) {
        String normalizedGenre = genre == null ? "" : genre.trim();

        if (normalizedGenre.isBlank()) {
            throw new IllegalArgumentException("Genre must not be blank");
        }

        log.debug("Fetching best movies by year for genre={}, page={}, size={}", normalizedGenre, page, size);

        return bestMoviesByYearRepository.findBestMoviesByYear(normalizedGenre, page, size);
    }
}

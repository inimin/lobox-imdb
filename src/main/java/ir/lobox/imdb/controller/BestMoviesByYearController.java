package ir.lobox.imdb.controller;

import ir.lobox.imdb.dto.PageResponse;
import ir.lobox.imdb.model.BestMovieByYear;
import ir.lobox.imdb.service.BestMoviesByYearService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movies")
public class BestMoviesByYearController {

    private final BestMoviesByYearService bestMoviesByYearService;

    public BestMoviesByYearController(BestMoviesByYearService bestMoviesByYearService) {
        this.bestMoviesByYearService = bestMoviesByYearService;
    }

    @GetMapping
    public PageResponse<BestMovieByYear> getBestMoviesByYear(
            @RequestParam @NotBlank @NotNull String genre,
            @RequestParam(defaultValue = "0") @Min(value = 0) int page,
            @RequestParam(defaultValue = "10") @Min(value = 1) @Max(value = 100) int size) {
        return bestMoviesByYearService.getBestMoviesByYear(genre, page, size);
    }
}

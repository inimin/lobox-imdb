package ir.lobox.imdb.controller;

import ir.lobox.imdb.dto.PageResponse;
import ir.lobox.imdb.model.TitleCrew;
import ir.lobox.imdb.service.TitleCrewService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/title")
public class TitleCrewController {

    private final TitleCrewService titleCrewService;

    public TitleCrewController(TitleCrewService titleCrewService) {
        this.titleCrewService = titleCrewService;
    }

    @GetMapping
    public PageResponse<TitleCrew> getAllPaged(
            @RequestParam(defaultValue = "0") @Min(value = 0) int page,
            @RequestParam(defaultValue = "10") @Min(value = 1) @Max(value = 100) int size) {
        return titleCrewService.getPagedTitleCrew(page, size);
    }
}

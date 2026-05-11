package ir.lobox.imdb.controller;

import ir.lobox.imdb.dto.PageResponse;
import ir.lobox.imdb.model.ActorTitlePair;
import ir.lobox.imdb.service.ActorPairsService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pairs")
public class ActorPairsController {

    private final ActorPairsService actorPairsService;

    public ActorPairsController(ActorPairsService actorPairsService) {
        this.actorPairsService = actorPairsService;
    }

    @GetMapping
    public PageResponse<ActorTitlePair> getActorPairs(
            @RequestParam @NotBlank @NotNull String actor1,
            @RequestParam @NotBlank @NotNull String actor2,
            @RequestParam(defaultValue = "0") @Min(value = 0) int page,
            @RequestParam(defaultValue = "10") @Min(value = 1) @Max(value = 100) int size) {

        return actorPairsService.findActorPairs(actor1, actor2, page, size);
    }
}

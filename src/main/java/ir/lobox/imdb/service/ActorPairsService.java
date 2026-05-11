package ir.lobox.imdb.service;

import ir.lobox.imdb.dto.PageResponse;
import ir.lobox.imdb.model.ActorTitlePair;
import ir.lobox.imdb.repository.ActorPairsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils; // Helper for string manipulation

@Service
public class ActorPairsService {

    private static final Logger log = LoggerFactory.getLogger(ActorPairsService.class);
    private final ActorPairsRepository actorPairsRepository;

    public ActorPairsService(ActorPairsRepository actorPairsRepository) {
        this.actorPairsRepository = actorPairsRepository;
    }

    public PageResponse<ActorTitlePair> findActorPairs(String actor1, String actor2, int page, int size) {
        return actorPairsRepository.findActorPairs(actor1, actor2, page, size);
    }
}

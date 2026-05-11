package ir.lobox.imdb.service;

import ir.lobox.imdb.dto.PageResponse;
import ir.lobox.imdb.model.TitleCrew;
import ir.lobox.imdb.repository.TitleCrewRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TitleCrewService {

    private static final Logger log = LoggerFactory.getLogger(TitleCrewService.class);
    private final TitleCrewRepository titleCrewRepository;

    public TitleCrewService(TitleCrewRepository titleCrewRepository) {
        this.titleCrewRepository = titleCrewRepository;
    }

    public PageResponse<TitleCrew> getPagedTitleCrew(int page, int size) {
        log.debug("Fetching TitleCrew page={}, size={}", page, size);

        PageResponse<TitleCrew> response = titleCrewRepository.findPaged(page, size);

        log.info("Returning {} records (page {}/{})",
                response.getContent().size(),
                response.getPage() + 1,
                response.getTotalPages());

        return response;
    }
}


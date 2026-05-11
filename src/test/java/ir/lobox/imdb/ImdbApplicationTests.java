package ir.lobox.imdb;

import ir.lobox.imdb.model.BestMovieByYear;
import ir.lobox.imdb.model.ActorTitlePair;
import ir.lobox.imdb.repository.ActorPairsRepository;
import ir.lobox.imdb.repository.BestMoviesByYearRepository;
import ir.lobox.imdb.repository.TitleCrewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ImdbApplicationTests {

	@Autowired
	private TitleCrewRepository titleCrewRepository;

	@Autowired
	private ActorPairsRepository actorPairsRepository;

	@Autowired
	private BestMoviesByYearRepository betterMoviesByYearRepository;

	@Test
	void contextLoads() {
		List<ActorTitlePair> movieList = actorPairsRepository.findActorPairs("Tom Hanks","Tim Allen");
		List<BestMovieByYear> movieList2 = betterMoviesByYearRepository.findBestMoviesByYear("Action");
		titleCrewRepository.findPaged();

	}

}

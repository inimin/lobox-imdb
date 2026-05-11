package ir.lobox.imdb;

import ir.lobox.imdb.model.Movie;
import ir.lobox.imdb.model.PageResult;
import ir.lobox.imdb.model.TitleCrew;
import ir.lobox.imdb.repository.ActorPairsRepository;
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

	@Test
	void contextLoads() {
		List<Movie> movieList = actorPairsRepository.findActorPairs("Tom Hanks","Tim Allen");
		titleCrewRepository.findPaged();

	}

}

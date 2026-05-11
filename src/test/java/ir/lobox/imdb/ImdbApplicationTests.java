package ir.lobox.imdb;

import ir.lobox.imdb.model.PageResult;
import ir.lobox.imdb.model.TitleCrew;
import ir.lobox.imdb.repository.TitleCrewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImdbApplicationTests {

	@Autowired
	private TitleCrewRepository titleCrewRepository;

	@Test
	void contextLoads() {
		titleCrewRepository.findPaged();
	}

}

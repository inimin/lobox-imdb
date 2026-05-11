package ir.lobox.imdb;

import ir.lobox.imdb.dto.PageResponse;
import ir.lobox.imdb.model.ActorTitlePair;
import ir.lobox.imdb.repository.ActorPairsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ImdbApplicationMovieTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ActorPairsRepository  actorPairsRepository;

	@Test
	void shouldReturnRealMoviesFromDatabase() throws Exception {

		mockMvc.perform(get("/api/movies")
						.param("genre", "Drama")
						.param("page", "0")
						.param("size", "5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray())
				.andExpect(jsonPath("$.content.length()").value(org.hamcrest.Matchers.greaterThan(0)));
	}

}

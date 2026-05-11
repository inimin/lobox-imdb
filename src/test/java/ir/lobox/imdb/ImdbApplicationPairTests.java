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
class ImdbApplicationPairTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ActorPairsRepository  actorPairsRepository;

	@Test
	void shouldReturnRealDataFromEndpoint() throws Exception {
		mockMvc.perform(get("/api/pairs")
						.param("actor1", "Tom Hanks")
						.param("actor2", "Meg Ryan")
						.param("page", "0")
						.param("size", "5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray())
				.andExpect(jsonPath("$.content").isNotEmpty());
	}


	@Test
	void repositoryShouldReturnRealData() {

		PageResponse<ActorTitlePair> result =
				actorPairsRepository.findActorPairs(
						"Tom Hanks",
						"Meg Ryan",
						0,
						5
				);

		assertThat(result).isNotNull();
		assertThat(result.getContent()).isNotEmpty();

		ActorTitlePair pair = result.getContent().get(0);

		assertThat(pair.getPrimaryTitle()).isNotBlank();
		assertThat(pair.getTconst()).startsWith("tt");
	}


	@Test
	void shouldReturnPaginationFields() throws Exception {

		mockMvc.perform(get("/api/pairs")
						.param("actor1", "Tom Hanks")
						.param("actor2", "Meg Ryan"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.page").value(0))
				.andExpect(jsonPath("$.size").value(10))
				.andExpect(jsonPath("$.content").exists());
	}

}

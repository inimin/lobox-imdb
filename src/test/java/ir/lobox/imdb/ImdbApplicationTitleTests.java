package ir.lobox.imdb;

import ir.lobox.imdb.repository.ActorPairsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ImdbApplicationTitleTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ActorPairsRepository  actorPairsRepository;

	@Test
	void shouldReturnRealTitleCrewData() throws Exception {

		mockMvc.perform(get("/api/title")
						.param("page", "0")
						.param("size", "5"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content").isArray())
				.andExpect(jsonPath("$.content.length()").value(org.hamcrest.Matchers.greaterThan(0)));
	}

}

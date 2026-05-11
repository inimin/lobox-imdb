package ir.lobox.imdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ImdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImdbApplication.class, args);
	}

}

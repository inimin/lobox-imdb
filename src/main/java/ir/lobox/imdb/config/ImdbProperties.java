package ir.lobox.imdb.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "imdb.data")
public class ImdbProperties {

    private String path;
    private String baseUrl;
    private List<String> files;

}

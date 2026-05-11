package ir.lobox.imdb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.List;


@Data
@Component
@ConfigurationProperties(prefix = "imdb.data")
public class ImdbProperties {

    private String path;
    private String baseUrl;
    private List<String> tableNames;
    private Format format;

    @Data
    public static class Format {
        private String base;
        private String target;
    }


    public String getTargetName(String tableName) {
        return tableName + "." + this.format.getTarget();
    }

    public String getBaseName(String tableName) {
        return  tableName + "." + this.format.getBase();
    }

    public String getTargetPath(String tableName) {
        return this.getPath() + "/" + this.getTargetName(tableName);
    }

    public String getBasePath(String tableName) {
        return this.getPath() + "/" + this.getBaseName(tableName);
    }

    public Path getAbsolutePath() {
        return Path.of(this.getPath()).toAbsolutePath();
    }

    public Path getAbsoluteBasePath(String tableName) {
        return getAbsolutePath().resolve(this.getBaseName(tableName));
    }

    public Path getAbsoluteTargetPath(String tableName) {
        return getAbsolutePath().resolve(this.getTargetName(tableName));
    }

    public String getTableUrl(String tableName) {
        return this.getBaseUrl() + getBaseName(tableName);
    }
}

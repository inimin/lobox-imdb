package ir.lobox.imdb.bootstrap;

import ir.lobox.imdb.config.ImdbProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;




@Component
@Order(1)
public class ImdbDatasetDownloader {

    private static final Logger log = LoggerFactory.getLogger(ImdbDatasetDownloader.class);

    private final ImdbProperties props;
    private final HttpClient httpClient;

    public ImdbDatasetDownloader(ImdbProperties props) {
        this.props = props;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void downloadAll() {
        try {
            Path directory = Paths.get(props.getPath());
            ensureDirectoryExists(directory);

            int existingCount = 0;
            int downloadedCount = 0;

            for (String tableName : props.getTableNames()) {
                Path baseFile = props.getAbsoluteBasePath(tableName);

                if (isValidExistingFile(baseFile)) {
                    log.info("Skipping downloading existing file: {}", baseFile.getFileName());
                    existingCount++;
                    continue;
                }

                download(tableName);
                downloadedCount++;
            }

            log.info("IMDb dataset download completed. Existing: {}, Newly downloaded: {}/{}",
                    existingCount, downloadedCount, props.getTableNames().size());

        } catch (Exception e) {
            log.error("Failed to download IMDb dataset", e);
            throw new RuntimeException("IMDb dataset download failed", e);
        }
    }

    private void ensureDirectoryExists(Path directory) throws IOException {
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
            log.info("Created directory: {}", directory);
        }
    }

    private boolean isValidExistingFile(Path file) throws IOException {
        return Files.exists(file) && Files.size(file) > 0;
    }

    private void download(String tableName) throws Exception {
        Path path = props.getAbsoluteBasePath(tableName);
        String fileName = props.getBaseName(tableName);

        String url = props.getTableUrl(tableName);
        log.info("Downloading: {} → {}", fileName, url);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .timeout(Duration.ofMinutes(10))
                .build();

        try (InputStream inputStream = httpClient.send(request,
                HttpResponse.BodyHandlers.ofInputStream()).body()) {

            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);
            log.info("Successfully downloaded {}", fileName);
        }
    }

}

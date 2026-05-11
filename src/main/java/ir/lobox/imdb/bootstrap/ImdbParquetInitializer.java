package ir.lobox.imdb.bootstrap;

import ir.lobox.imdb.config.ImdbProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
@Order(2)
public class ImdbParquetInitializer {

    private static final Logger log = LoggerFactory.getLogger(ImdbParquetInitializer.class);

    private final ImdbProperties props;
    private final JdbcTemplate jdbcTemplate;

    @EventListener(ApplicationReadyEvent.class)
    public void convertAllToParquet() {
        log.info("Starting IMDb TSV.GZ → Parquet conversion...");

        Path basePath = Path.of(props.getPath()).toAbsolutePath();

        int converted = 0;
        int skipped = 0;
        int missing = 0;

        for (String tsvFileName : props.getFiles()) {

            Path tsvPath = basePath.resolve(tsvFileName);
            String parquetFileName = tsvFileName.replace(".tsv.gz", ".parquet");
            Path parquetPath = basePath.resolve(parquetFileName);

            if (Files.exists(parquetPath)) {
                log.info("Skipping converting {} (Parquet file already exists)", tsvFileName);
                skipped++;
                continue;
            }

            if (!Files.exists(tsvPath)) {
                log.warn("Source file not found: {}", tsvPath);
                missing++;
                continue;
            }

            try {
                log.info("Converting: {} → {}", tsvPath.getFileName(), parquetPath.getFileName());

                String sql = buildCopySql(tsvPath.toAbsolutePath().toString(),
                        parquetPath.toAbsolutePath().toString());

                jdbcTemplate.execute(sql);

                log.info("Successfully converted: {}", tsvFileName);
                converted++;

            } catch (Exception e) {
                log.error("Failed to convert: {}", tsvFileName, e);
            }
        }

        log.info("IMDb Parquet conversion completed. Converted: {}, Skipped: {}, Missing: {}",
                converted, skipped, missing);
    }

    private String buildCopySql(String inputFile, String outputFile) {
        return """
                COPY (
                    SELECT *
                    FROM read_csv_auto('%s', 
                         delim = '\t', 
                         header = true, 
                         compression = 'gzip')
                ) TO '%s' (FORMAT PARQUET, COMPRESSION 'SNAPPY');
                """.formatted(inputFile, outputFile);
    }
}

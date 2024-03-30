package im.pickside.server.topic.validation;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j(topic = "BadWordsHolder")
@Component
public class BadWordsHolder {

    public static final String PATH_TO_BAD_WORDS = "src/main/resources/bad-words.txt";

    private List<String> badWords;

    @PostConstruct
    public void init() {
        try {
            Path paths = Paths.get(PATH_TO_BAD_WORDS);
            badWords = Files.readAllLines(paths);
        } catch (IOException ex) {
            log.error("Failed to read bad-words.txt", ex);
        }
    }

    protected boolean containsBadWords(String content) {
        return badWords.stream().anyMatch(content::contains);
    }

}

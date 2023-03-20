import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ReadJsonTest {

    private ClassLoader classLoader = ReadFilesTest.class.getClassLoader();

    @Test
    @Tag("JSON")
    void readGameFromJsonTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = classLoader.getResourceAsStream("game.json");
             InputStreamReader isr = new InputStreamReader(is)) {
            Game game = mapper.readValue(isr, Game.class);

            Assertions.assertEquals("The last of us", game.name);
            Assertions.assertEquals("Naughty Dog", game.developer);
            Assertions.assertEquals("action-adventure", game.genre);
            Assertions.assertEquals("10.12.2011", game.date);
            Assertions.assertEquals("Neil Druckmann", game.writer);
            Assertions.assertEquals(10, game.score);
            Assertions.assertTrue(game.theBast);
            Assertions.assertEquals(List.of("PS3", "PS4", "PS5"), game.platforms);
        }
    }
}

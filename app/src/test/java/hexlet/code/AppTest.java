package hexlet.code;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    void testAppOutput() {
        // Заменяем стандартный поток вывода
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // Вызываем main метод
        App.main(new String[]{});

        // Возвращаем стандартный поток вывода
        System.setOut(System.out);

        // Проверяем содержимое вывода
        assertThat(outputStream.toString().trim()).isEqualTo("Hello world!");
    }
}

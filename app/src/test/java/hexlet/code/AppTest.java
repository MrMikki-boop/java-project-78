package hexlet.code;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testPrintsHelloWorld() {
        // Перенаправляем стандартный вывод в поток
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Вызываем метод, который выводит "Hello world!"
        App.main(new String[]{});

        // Получаем вывод из потока и восстанавливаем стандартный вывод
        String actualOutput = outContent.toString().trim();
        System.setOut(System.out);

        // Сравниваем ожидаемый вывод с фактическим
        assertEquals("Hello world!", actualOutput);
    }
}

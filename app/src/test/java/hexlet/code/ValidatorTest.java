package hexlet.code;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    @Test
    void testStringSchemaValidation() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        // Проверка пустой строки и null до установки required
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));

        schema.required();

        // Проверка null после установки required
        assertFalse(schema.isValid(null));

        // В этом случае, ожидается, что пустая строка не будет валидной
        assertFalse(schema.isValid(""));

        // Проверка minLength
        assertFalse(schema.minLength(6).isValid("short"));
        assertTrue(schema.isValid("hexlet"));

        // Проверка contains
        schema.contains("wh");
        assertTrue(schema.isValid("what does the fox say"));

        // Дополнительная проверка contains
        schema.contains("whatthe");
        assertFalse(schema.isValid("what does the fox say"));

        // Проверка сочетания условий
        assertFalse(schema.isValid("what does the fox say"));
    }

    @Test
    void testNumberSchemaValidation() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertTrue(schema.isValid(10)); // Пример успешной валидации
        assertFalse(schema.isValid(null)); // Пример неуспешной валидации, так как required()
        assertFalse(schema.isValid("not a number")); // Пример неуспешной валидации, так как не число
        assertFalse(schema.positive().isValid(-10)); // Пример неуспешной валидации, так как отрицательное число
        assertTrue(schema.range(5, 10).isValid(7)); // Пример успешной валидации в заданном диапазоне
        assertFalse(schema.range(5, 10).isValid(11)); // Пример неуспешной валидации вне заданного диапазона
    }

    @Test
    void testMapSchemaValidation() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        // Проверка null до установки required
        assertTrue(schema.isValid(null));

        schema.required();

        // Проверка null после установки required
        assertFalse(schema.isValid(null));

        // Проверка пустой Map
        assertTrue(schema.isValid(new HashMap<>()));

        // Проверка Map с парами ключ-значение
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data));

        // Проверка sizeof
        schema.sizeof(2);
        assertFalse(schema.isValid(data));

        data.put("key2", "value2");
        assertTrue(schema.isValid(data));
    }
}

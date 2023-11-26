package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class ValidatorTest {
    private static final Map<String, String> MAP_1 = Map.of(
            "key1",
            "value1",
            "key2",
            "value2",
            "key3",
            "value3"
    );
    private static final Map<Integer, Integer> MAP_2 = Map.of(
            1,
            123,
            2,
            456
    );

    private Validator validator;
    private StringSchema stringSchema;
    private NumberSchema numberSchema;
    private MapSchema mapSchema;

    @BeforeEach
    void beforeEach() {
        validator = new Validator();
    }

    @Test
    void stringSchemaTestDefault() {
        // Проверяем, что StringSchema по умолчанию считается валидным для null и пустой строки
        assertTrue(validator.string().isValid(null));
        assertTrue(validator.string().isValid(""));
        assertTrue(validator.string().isValid("fox"));
        assertTrue(validator.string().isValid("what does the fox say"));
    }

    @Test
    void stringSchemaTestRequired() {
        // Проверяем, что StringSchema, помеченный как обязательный, считает null и пустую строку невалидными
        assertFalse(validator.string().required().isValid(null));
        assertFalse(validator.string().required().isValid(""));
        assertTrue(validator.string().required().isValid("fox"));
        assertTrue(validator.string().required().isValid("what does the fox say"));
    }

    @Test
    void stringSchemaTestMinLength() {
        // Проверяем, что StringSchema с ограничением на минимальную длину корректно валидирует строки
        assertTrue(validator.string().minLength(5).isValid(null));
        assertFalse(validator.string().minLength(5).isValid(""));
        assertFalse(validator.string().minLength(5).isValid("fox"));
        assertTrue(validator.string().minLength(5).isValid("what does the fox say"));

        // Устанавливаем новую минимальную длину и проверяем валидацию
        validator.string().minLength(3);
        assertTrue(validator.string().minLength(3).isValid("fox"));
    }

    @Test
    void stringSchemaTestContains() {
        // Проверяем, что StringSchema с ограничением на наличие подстроки корректно валидирует строки
        assertFalse(validator.string().contains("fox").isValid(null));
        assertFalse(validator.string().contains("fox").isValid(""));
        assertTrue(validator.string().contains("fox").isValid("fox"));
        assertTrue(validator.string().contains("fox").isValid("what does the fox say"));

        // Устанавливаем новую подстроку и проверяем валидацию
        validator.string().contains("what");
        assertFalse(validator.string().contains("what").isValid("fox"));
        assertTrue(validator.string().contains("what").isValid("what does the fox say"));

        // Устанавливаем другую новую подстроку и проверяем валидацию
        validator.string().contains("something else");
        assertFalse(validator.string().contains("something else").isValid("what does the fox say"));
    }

    @Test
    void stringSchemaTestAllMethods() {
        // Проверяем, что StringSchema совместно использует все установленные ограничения
        assertFalse(validator.string().required().minLength(5).isValid(null));
        assertFalse(validator.string().required().minLength(5).isValid(""));
        assertFalse(validator.string().required().minLength(5).isValid("fox"));
        assertTrue(validator.string().required().minLength(5).isValid("what does the fox say"));

        // Проверяем, что метод contains также учитывается при валидации
        validator.string().contains("fox");
        assertFalse(validator.string().required().minLength(5).isValid("fox"));
        assertTrue(validator.string().required().minLength(5).isValid("what does the fox say"));
    }

    @Test
    void numberSchemaTestDefault() {
        // Проверяем, что NumberSchema по умолчанию считается валидным для null и чисел
        assertTrue(validator.number().isValid(null));
        assertTrue(validator.number().isValid(10));
        assertTrue(validator.number().isValid(-10));
        assertFalse(validator.number().isValid("10"));
    }

    @Test
    void numberSchemaTestRequired() {
        // Проверяем, что NumberSchema, помеченный как обязательный, считает null невалидным
        assertFalse(validator.number().required().isValid(null));
        assertTrue(validator.number().required().isValid(10));
        assertTrue(validator.number().required().isValid(-10));
        assertFalse(validator.number().required().isValid("10"));
    }

    @Test
    void numberSchemaTestPositive() {
        // Проверяем, что NumberSchema с ограничением на положительные числа корректно валидирует числа
        assertTrue(validator.number().positive().isValid(null));
        assertTrue(validator.number().positive().isValid(10));
        assertFalse(validator.number().positive().isValid(-10));
        assertFalse(validator.number().positive().isValid("10"));
    }

    @Test
    void numberSchemaTestRange() {
        // Проверяем, что NumberSchema с ограничением на диапазон корректно валидирует числа
        assertTrue(validator.number().range(-10, 0).isValid(null));
        assertFalse(validator.number().range(-10, 0).isValid(10));
        assertTrue(validator.number().range(-10, 0).isValid(-10));
        assertTrue(validator.number().range(-10, 0).isValid(0));
        assertFalse(validator.number().range(-10, 0).isValid("10"));
    }

    @Test
    void numberSchemaTestAllMethods() {
        // Проверяем, что NumberSchema совместно использует все установленные ограничения
        assertFalse(validator.number().required().positive().range(-10, 0).isValid(null));
        assertFalse(validator.number().required().positive().range(-10, 0).isValid(10));
        assertFalse(validator.number().required().positive().range(-10, 0).isValid(-10));
        assertFalse(validator.number().required().positive().range(-10, 0).isValid("10"));
    }

    @Test
    void mapSchemaTestDefault() {
        // Проверяем, что MapSchema по умолчанию считается валидным для null и пустой карты
        assertTrue(validator.map().isValid(null));
        assertTrue(validator.map().isValid(new HashMap<>()));
        assertTrue(validator.map().isValid(MAP_1));
        assertTrue(validator.map().isValid(MAP_2));
    }

    @Test
    void mapSchemaTestRequired() {
        // Проверяем, что MapSchema, помеченный как обязательный, считает null невалидным
        assertFalse(validator.map().required().isValid(null));
        assertTrue(validator.map().required().isValid(new HashMap<>()));
        assertTrue(validator.map().required().isValid(MAP_1));
        assertTrue(validator.map().required().isValid(MAP_2));
    }

    @Test
    void mapSchemaTestSizeOf() {
        // Проверяем, что MapSchema с ограничением на размер корректно валидирует карты
        assertTrue(validator.map().sizeof(2).isValid(null));
        assertFalse(validator.map().sizeof(2).isValid(new HashMap<>()));
        assertFalse(validator.map().sizeof(2).isValid(MAP_1));
        assertTrue(validator.map().sizeof(2).isValid(MAP_2));
    }

    @Test
    void mapSchemaTestBothMethods() {
        // Проверяем, что MapSchema совместно использует оба установленных ограничения
        assertFalse(validator.map().required().sizeof(2).isValid(null));
        assertFalse(validator.map().required().sizeof(2).isValid(new HashMap<>()));
        assertFalse(validator.map().required().sizeof(2).isValid(MAP_1));
        assertTrue(validator.map().required().sizeof(2).isValid(MAP_2));
    }

    @Test
    void mapSchemaTestShape() {
        // Проверяем, что MapSchema с ограничением на форму корректно валидирует карты
        mapSchema = validator.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number().positive());
        mapSchema.shape(schemas);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "Maksim");
        map3.put("age", 19);
        assertTrue(mapSchema.isValid(map3));

        map3.put("name", 19);
        map3.put("age", "Maksim");
        assertFalse(mapSchema.isValid(map3));
    }
}

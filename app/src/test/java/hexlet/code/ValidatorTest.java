package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.MapSchema;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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

    // Инициализация объектов валидатора и схем валидации перед каждым тестом
    private Validator validator;
    private StringSchema stringSchema;
    private NumberSchema numberSchema;
    private MapSchema mapSchema;

    @BeforeEach
    void beforeEach() {
        // Инициализация валидатора и схем перед каждым тестом
        validator = new Validator();
        stringSchema = validator.string();
        numberSchema = validator.number();
        mapSchema = validator.map();
    }

    // Тестирование схемы для строк
    @Test
    void testStringValidator() {
        // Проверка, что схема валидирует значения по умолчанию
        assertThat(stringSchema.isValid(null)).isTrue();
        assertThat(stringSchema.isValid("")).isTrue();

        // Установка обязательности и проверка валидации
        stringSchema.required();
        assertThat(stringSchema.isValid(null)).isFalse();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        // Установка минимальной длины и проверка валидации
        stringSchema.minLength(5);
        assertThat(stringSchema.isValid(null)).isFalse();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        // Установка подстроки и проверка валидации
        stringSchema.contains("fox");
        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
    }

    // Тестирование схемы для чисел
    @Test
    void testNumberValidator() {
        // Проверка, что схема валидирует значения по умолчанию
        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isTrue();
        assertThat(numberSchema.isValid("10")).isFalse();

        // Проверка, что схема валидирует обязательные значения
        numberSchema = validator.number().required();
        assertThat(numberSchema.isValid(null)).isFalse();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isTrue();
        assertThat(numberSchema.isValid("10")).isFalse();

        // Проверка, что схема валидирует положительные значения
        numberSchema = validator.number().positive();
        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isFalse();
        assertThat(numberSchema.isValid("10")).isFalse();

        // Проверка, что схема валидирует значения в заданном диапазоне
        numberSchema = validator.number().range(-10, 0);
        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isFalse();
        assertThat(numberSchema.isValid(-10)).isTrue();
        assertThat(numberSchema.isValid(0)).isTrue();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    // Тестирование схемы для карты (Map)
    @Test
    void testMapValidator() {
        // Проверка, что схема валидирует значения по умолчанию
        assertThat(mapSchema.isValid(null)).isTrue();
        assertThat(mapSchema.isValid(new HashMap<>())).isTrue();
        assertThat(mapSchema.isValid(MAP_1)).isTrue();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();

        // Проверка, что схема валидирует обязательные значения
        mapSchema.required();
        assertThat(mapSchema.isValid(null)).isFalse();
        assertThat(mapSchema.isValid(new HashMap<>())).isTrue();
        assertThat(mapSchema.isValid(MAP_1)).isTrue();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();

        // Проверка, что схема валидирует по размеру
        mapSchema.sizeof(2);
        assertThat(mapSchema.isValid(null)).isFalse();
        assertThat(mapSchema.isValid(new HashMap<>())).isFalse();
        assertThat(mapSchema.isValid(MAP_1)).isFalse();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();

        // Проверка, что схема валидирует структуру карты
        mapSchema.shape(Map.of("key1", validator.string().required(), "key2", validator.number().positive()));
        Map<String, Object> validMap = Map.of("key1", "value1", "key2", 123);
        Map<String, Object> invalidMap = Map.of("key1", 123, "key2", "value2");

        assertThat(mapSchema.isValid(validMap)).isTrue();
        assertThat(mapSchema.isValid(invalidMap)).isFalse();

        // Старая проверка, что схема валидирует структуру карты
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number().positive());

        mapSchema = validator.map();  // Создание новой схемы перед каждым тестом
        mapSchema.shape(schemas);

        // Пример валидной карты
        validMap = new HashMap<>();
        validMap.put("name", "Maksim");
        validMap.put("age", 19);
        assertThat(mapSchema.isValid(validMap)).isTrue();

        // Пример карты с невалидными значениями
        invalidMap = new HashMap<>();
        invalidMap.put("name", 19);
        invalidMap.put("age", "Maksim");
        assertThat(mapSchema.isValid(invalidMap)).isFalse();
    }
}

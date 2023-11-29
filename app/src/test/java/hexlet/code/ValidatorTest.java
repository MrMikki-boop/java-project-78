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
    void shouldValidateDefaultValues() {
        // Проверка, что схема валидирует значения по умолчанию
        assertThat(stringSchema.isValid(null)).isTrue();
        assertThat(stringSchema.isValid("")).isTrue();
        assertThat(stringSchema.isValid("fox")).isTrue();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
    }

    @Test
    void shouldValidateRequiredValues() {
        // Установка обязательности и проверка валидации
        stringSchema.required();

        assertThat(stringSchema.isValid(null)).isFalse();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("fox")).isTrue();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
    }

    @Test
    void stringSchemaTestMinLength() {
        // Установка минимальной длины и проверка валидации
        stringSchema.minLength(5).required().contains("fox");

        assertThat(stringSchema.isValid(null)).isTrue();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        // Изменение минимальной длины и повторная проверка
        stringSchema.minLength(3);
        assertThat(stringSchema.isValid("fox")).isTrue();
    }

    @Test
    void stringSchemaTestContains() {
        // Установка подстроки и проверка валидации
        StringSchema result = stringSchema.contains("fox");

        assertThat(stringSchema.isValid(null)).isFalse();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("fox")).isTrue();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        // Изменение подстроки и повторная проверка
        stringSchema.contains("what");
        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        // Изменение подстроки на несуществующую и повторная проверка
        stringSchema.contains("something else");
        assertThat(stringSchema.isValid("what does the fox say")).isFalse();
    }

    @Test
    void stringSchemaTestAllMethods() {
        // Установка обязательности и минимальной длины и проверка сочетания различных методов
        stringSchema.required().minLength(5);

        assertThat(stringSchema.isValid(null)).isFalse();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        // Изменение подстроки и повторная проверка
        stringSchema.contains("fox");
        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
    }

    // Тестирование схемы для чисел
    @Test
    void numberSchemaTestDefault() {
        // Проверка, что схема валидирует значения по умолчанию
        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isTrue();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    @Test
    void numberSchemaTestRequired() {
        // Проверка, что схема валидирует обязательные значения
        numberSchema.required();

        assertThat(numberSchema.isValid(null)).isFalse();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isTrue();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    @Test
    void numberSchemaTestPositive() {
        // Проверка, что схема валидирует положительные значения
        numberSchema.positive();

        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isFalse();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    @Test
    void numberSchemaTestRange() {
        // Проверка, что схема валидирует значения в заданном диапазоне
        numberSchema.range(-10, 0);

        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isFalse();
        assertThat(numberSchema.isValid(-10)).isTrue();
        assertThat(numberSchema.isValid(0)).isTrue();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    @Test
    void numberSchemaTestAllMethods() {
        // Проверка, что схема валидирует сочетание различных методов
        numberSchema.required().positive().range(-10, 0);

        assertThat(numberSchema.isValid(null)).isFalse();
        assertThat(numberSchema.isValid(10)).isFalse();
        assertThat(numberSchema.isValid(-10)).isFalse();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    // Тестирование схемы для карты (Map)
    @Test
    void mapSchemaTestDefault() {
        // Проверка, что схема валидирует значения по умолчанию
        assertThat(mapSchema.isValid(null)).isTrue();
        assertThat(mapSchema.isValid(new HashMap<>())).isTrue();
        assertThat(mapSchema.isValid(MAP_1)).isTrue();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();
    }

    @Test
    void mapSchemaTestRequired() {
        // Проверка, что схема валидирует обязательные значения
        mapSchema.required();

        assertThat(mapSchema.isValid(null)).isFalse();
        assertThat(mapSchema.isValid(new HashMap<>())).isTrue();
        assertThat(mapSchema.isValid(MAP_1)).isTrue();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();
    }

    @Test
    void mapSchemaTestSizeOf() {
        // Проверка, что схема валидирует по размеру
        mapSchema.sizeof(2);

        assertThat(mapSchema.isValid(null)).isTrue();
        assertThat(mapSchema.isValid(new HashMap<>())).isFalse();
        assertThat(mapSchema.isValid(MAP_1)).isFalse();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();
    }

    @Test
    void mapSchemaTestBothMethods() {
        // Проверка, что схема валидирует по обоим методам
        mapSchema.required().sizeof(2);

        assertThat(mapSchema.isValid(null)).isFalse();
        assertThat(mapSchema.isValid(new HashMap<>())).isFalse();
        assertThat(mapSchema.isValid(MAP_1)).isFalse();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();
    }

    @Test
    void mapSchemaTestShape() {
        // Проверка, что схема валидирует структуру карты
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number().positive());
        mapSchema.shape(schemas);

        // Пример валидной карты
        Map<String, Object> validMap = new HashMap<>();
        validMap.put("name", "Maksim");
        validMap.put("age", 19);
        assertThat(mapSchema.isValid(validMap)).isTrue();

        // Пример карты с невалидными значениями
        Map<String, Object> invalidMap = new HashMap<>();
        invalidMap.put("name", 19);
        invalidMap.put("age", "Maksim");
        assertThat(mapSchema.isValid(invalidMap)).isFalse();
    }
}

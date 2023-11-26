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
        stringSchema = validator.string();

        assertThat(stringSchema.isValid(null)).isTrue();
        assertThat(stringSchema.isValid("")).isTrue();
        assertThat(stringSchema.isValid("fox")).isTrue();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
    }

    @Test
    void stringSchemaTestRequired() {
        stringSchema = validator.string().required();

        assertThat(stringSchema.isValid(null)).isFalse();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("fox")).isTrue();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
    }

    @Test
    void stringSchemaTestMinLength() {
        stringSchema = validator.string().minLength(5);

        assertThat(stringSchema.isValid(null)).isTrue();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        stringSchema.minLength(3);

        assertThat(stringSchema.isValid("fox")).isTrue();
    }

    @Test
    void stringSchemaTestContains() {
        stringSchema = validator.string().contains("fox");

        assertThat(stringSchema.isValid(null)).isFalse();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("fox")).isTrue();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        stringSchema.contains("what");

        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        stringSchema.contains("something else");

        assertThat(stringSchema.isValid("what does the fox say")).isFalse();
    }

    @Test
    void stringSchemaTestAllMethods() {
        stringSchema = validator.string().required().minLength(5);

        assertThat(stringSchema.isValid(null)).isFalse();
        assertThat(stringSchema.isValid("")).isFalse();
        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();

        stringSchema.contains("fox");

        assertThat(stringSchema.isValid("fox")).isFalse();
        assertThat(stringSchema.isValid("what does the fox say")).isTrue();
    }

    @Test
    void numberSchemaTestDefault() {
        numberSchema = validator.number();

        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isTrue();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    @Test
    void numberSchemaTestRequired() {
        numberSchema = validator.number().required();

        assertThat(numberSchema.isValid(null)).isFalse();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isTrue();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    @Test
    void numberSchemaTestPositive() {
        numberSchema = validator.number().positive();

        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isTrue();
        assertThat(numberSchema.isValid(-10)).isFalse();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    @Test
    void numberSchemaTestRange() {
        numberSchema = validator.number().range(-10, 0);

        assertThat(numberSchema.isValid(null)).isTrue();
        assertThat(numberSchema.isValid(10)).isFalse();
        assertThat(numberSchema.isValid(-10)).isTrue();
        assertThat(numberSchema.isValid(0)).isTrue();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    @Test
    void numberSchemaTestAllMethods() {
        numberSchema = validator.number().required().positive().range(-10, 0);

        assertThat(numberSchema.isValid(null)).isFalse();
        assertThat(numberSchema.isValid(10)).isFalse();
        assertThat(numberSchema.isValid(-10)).isFalse();
        assertThat(numberSchema.isValid("10")).isFalse();
    }

    @Test
    void mapSchemaTestDefault() {
        mapSchema = validator.map();

        assertThat(mapSchema.isValid(null)).isTrue();
        assertThat(mapSchema.isValid(new HashMap<>())).isTrue();
        assertThat(mapSchema.isValid(MAP_1)).isTrue();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();
    }

    @Test
    void mapSchemaTestRequired() {
        mapSchema = validator.map().required();

        assertThat(mapSchema.isValid(null)).isFalse();
        assertThat(mapSchema.isValid(new HashMap<>())).isTrue();
        assertThat(mapSchema.isValid(MAP_1)).isTrue();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();
    }

    @Test
    void mapSchemaTestSizeOf() {
        mapSchema = validator.map().sizeof(2);

        assertThat(mapSchema.isValid(null)).isTrue();
        assertThat(mapSchema.isValid(new HashMap<>())).isFalse();
        assertThat(mapSchema.isValid(MAP_1)).isFalse();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();
    }

    @Test
    void mapSchemaTestBothMethods() {
        mapSchema = validator.map().required().sizeof(2);

        assertThat(mapSchema.isValid(null)).isFalse();
        assertThat(mapSchema.isValid(new HashMap<>())).isFalse();
        assertThat(mapSchema.isValid(MAP_1)).isFalse();
        assertThat(mapSchema.isValid(MAP_2)).isTrue();
    }

    @Test
    void mapSchemaTestShape() {
        mapSchema = validator.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number().positive());
        mapSchema.shape(schemas);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "Eliza");
        map3.put("age", 24);
        assertThat(mapSchema.isValid(map3)).isTrue();

        map3.put("name", 24);
        map3.put("age", "Eliza");
        assertThat(mapSchema.isValid(map3)).isFalse();
    }
}
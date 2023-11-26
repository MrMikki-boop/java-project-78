package hexlet.code;

import hexlet.code.schemas.BaseSchema;
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
    void testMapSchemaWithShapeValidation() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        // Определяем схемы валидации для значений свойств "name" и "age"
        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());

        // Передаем созданный набор схем в метод shape()
        schema.shape(schemas);

        // Проверяем объекты
        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);
        assertFalse(schema.isValid(human1), "human1 should be valid");

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertFalse(schema.isValid(human2), "human2 should be valid");

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertFalse(schema.isValid(human3), "human3 should not be valid");

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", -5);
        assertFalse(schema.isValid(human4), "human4 should not be valid");
    }
}

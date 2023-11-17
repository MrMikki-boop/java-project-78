package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

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

        // Проверка null и пустой строки после установки required
        assertFalse(schema.isValid(null));
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
}

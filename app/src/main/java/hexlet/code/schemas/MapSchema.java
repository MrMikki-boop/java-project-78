package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema {
    private boolean requireNonNull = false;
    private Integer sizeConstraint;
    private Map<String, BaseSchema> shapeSchemas;

    public MapSchema required() {
        requireNonNull = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        sizeConstraint = size;
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> schemas) {
        shapeSchemas = schemas;
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        if (requireNonNull && value == null) {
            return false;
        }

        if (value == null) {
            return !required;
        }

        if (!(value instanceof Map<?, ?>)) {
            return false;
        }

        Map<?, ?> mapValue = (Map<?, ?>) value;

        if (requireNonNull && mapValue.containsValue(null)) {
            return false;
        }

        if (sizeConstraint != null && mapValue.size() != sizeConstraint) {
            return false;
        }

        if (shapeSchemas != null) {
            for (Map.Entry<String, BaseSchema> entry : shapeSchemas.entrySet()) {
                String key = entry.getKey();
                BaseSchema schema = entry.getValue();

                // Добавляем проверку на null перед вызовом isValid для вложенных схем
                if (schema == null) {
                    return false;
                }

                Object nestedValue = mapValue.get(key);
                if (nestedValue == null || !schema.isValid(nestedValue)) {
                    return false;
                }
            }
        }

        return true; // Возвращаем true только если все проверки прошли успешно
    }
}

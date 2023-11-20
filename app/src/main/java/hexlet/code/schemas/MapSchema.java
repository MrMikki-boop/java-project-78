package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema {
    private boolean requireNonNull = false;
    private Integer sizeConstraint;

    public void required() {
        requireNonNull = true;
    }

    public void sizeof(int size) {
        sizeConstraint = size;
    }

    @Override
    public boolean isValid(Object value) {
        if (requireNonNull && value == null) {
            return false;
        }

        if (value == null) {
            return !required; // Изменено: isValid(null) возвращает true, если не установлено required()
        }

        if (!(value instanceof Map)) {
            return false;
        }

        Map<?, ?> mapValue = (Map<?, ?>) value;

        if (requireNonNull && mapValue.containsValue(null)) {
            return false;
        }

        return sizeConstraint == null || mapValue.size() == sizeConstraint;
    }

}

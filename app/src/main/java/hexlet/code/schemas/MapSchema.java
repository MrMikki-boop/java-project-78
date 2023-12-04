package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema {

    // Константы для определения типа данных, обязательности, размера и формы
    private static final String DATA_TYPE = "dataType";
    private static final String REQUIRED = "required";
    private static final String SIZE_OF = "sizeOf";
    private static final String SHAPE = "shape";

    // Конструктор класса MapSchema
    public MapSchema() {
        addCheck(DATA_TYPE, value -> (value instanceof Map) || value == null);
    }

    // Метод для установки обязательности значения
    public MapSchema required() {
        addCheck(REQUIRED, value -> value instanceof Map);
        return this;
    }

    // Метод для установки размера отображения
    public MapSchema sizeof(int number) {
        addCheck(SIZE_OF, value -> value == null || ((Map<?, ?>) value).size() == number);
        return this;
    }

    // Метод для установки формы отображения
    public void shape(Map<String, BaseSchema> map) {
        addCheck(SHAPE, value -> map.entrySet().stream()
                .allMatch(entry -> entry.getValue().isValid(((Map<?, ?>) value).get(entry.getKey()))));
    }
}

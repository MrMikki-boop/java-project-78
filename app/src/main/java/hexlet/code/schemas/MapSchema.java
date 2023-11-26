package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema {
    private static final String DATA_TYPE = "dataType";
    private static final String REQUIRED = "required";
    private static final String SIZE_OF = "sizeOf";
    private static final String SHAPE = "shape";

    private int size;
    private Map<String, BaseSchema> schemas;

    public MapSchema() {
        addCheck(DATA_TYPE, value -> (value instanceof Map) || value == null);
    }

    public MapSchema required() {
        addCheck(REQUIRED, value -> value instanceof Map);
        return this;
    }

    public MapSchema sizeof(int number) {
        size = number;
        addCheck(SIZE_OF, value -> value == null || ((Map) value).size() == size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema> map) {
        schemas = map;
        addCheck(SHAPE, value -> schemas.entrySet().stream()
                .allMatch(entry -> entry.getValue().isValid(((Map) value).get(entry.getKey()))));
        return this;
    }
}

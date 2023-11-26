package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {

    private static final String DATA_TYPE = "dataType";
    private static final String REQUIRED = "required";
    private static final String MIN_LENGTH = "minLength";
    private static final String CONTAINS = "contains";

    private int minLengthNumber;
    private String content;

    public StringSchema() {
        addCheck(DATA_TYPE, value -> (value instanceof String) || value == null);
    }

    public StringSchema required() {
        addCheck(REQUIRED, value -> (value instanceof String) && (!value.equals("")));
        return this;
    }

    public StringSchema minLength(int number) {
        minLengthNumber = number;
        addCheck(MIN_LENGTH, value -> (value == null) || (value.toString().length() >= minLengthNumber));
        return this;
    }

    public StringSchema contains(String string) {
        content = string;
        addCheck(CONTAINS, value -> (value != null) && (value.toString().contains(content)));
        return this;
    }

}

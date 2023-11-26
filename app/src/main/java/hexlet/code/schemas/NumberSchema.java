package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {

    private static final String DATA_TYPE = "dataType";
    private static final String REQUIRED = "required";
    private static final String POSITIVE = "positive";
    private static final String RANGE = "range";

    private int firstNumber;
    private int lastNumber;

    public NumberSchema() {
        addCheck(DATA_TYPE, value -> (value instanceof Integer) || (value == null));
    }

    public NumberSchema required() {
        addCheck(REQUIRED, value -> value instanceof Integer);
        return this;
    }

    public NumberSchema positive() {
        addCheck(POSITIVE, value -> (value == null) || ((int) value > 0));
        return this;
    }

    public NumberSchema range(Integer first, Integer last) {
        firstNumber = first;
        lastNumber = last;
        addCheck(RANGE, value -> (value == null) || ((int) value >= firstNumber && (int) value <= lastNumber));
        return this;
    }

}

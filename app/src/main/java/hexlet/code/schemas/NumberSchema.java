package hexlet.code.schemas;

public class NumberSchema extends BaseSchema {
    private boolean positive = false;
    private Integer min;
    private Integer max;

    public NumberSchema positive() {
        positive = true;
        return this;
    }

    public NumberSchema range(int minValue, int maxValue) {
        this.min = minValue;
        this.max = maxValue;
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        if (required && value == null) {
            return false;
        }

        if (!(value instanceof Number)) {
            return false;
        }

        int intValue = ((Number) value).intValue();

        if (positive && intValue <= 0) {
            return false;
        }

        return (min == null || intValue >= min) && (max == null || intValue <= max);
    }
}

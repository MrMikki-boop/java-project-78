package hexlet.code.schemas;

public class StringSchema extends BaseSchema {
    private Integer minLength;
    private String contains;

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String substring) {
        this.contains = substring;
        return this;
    }

    @Override
    public boolean isValid(Object value) {
        if (required && (value == null || value.toString().isEmpty())) {
            return false;
        }

        String stringValue = (String) value;

        if (minLength != null && stringValue.length() < minLength) {
            return false;
        }

        return contains == null || stringValue.contains(contains);
    }
}

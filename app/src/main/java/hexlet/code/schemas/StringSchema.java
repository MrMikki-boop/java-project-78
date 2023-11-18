package hexlet.code.schemas;

public class StringSchema extends BaseSchema {
    private Integer minLength;
    private String contains;

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }

    public void contains(String substring) {
        this.contains = substring;
    }

    @Override
    public boolean isValid(Object value) {
        if (!(value instanceof String stringValue)) {
            return false;
        }

        if (required && stringValue.isEmpty()) {
            return false;
        }

        if (minLength != null && stringValue.length() < minLength) {
            return false;
        }

        return contains == null || stringValue.contains(contains);
    }

}

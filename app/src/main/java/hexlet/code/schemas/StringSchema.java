package hexlet.code.schemas;

public class StringSchema {
    private boolean required = false;
    private Integer minLength;
    private String contains;

    public void required() {
        required = true;
    }

    public StringSchema minLength(int length) {
        minLength = length;
        return this;
    }

    public void contains(String substring) {
        contains = substring;
    }

    public boolean isValid(String value) {
        if (required && (value == null || value.isEmpty())) {
            return false;
        }

        if (minLength != null && value.length() < minLength) {
            return false;
        }

        return contains == null || value.contains(contains);
    }
}

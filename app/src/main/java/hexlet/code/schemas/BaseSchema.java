package hexlet.code.schemas;

public abstract class BaseSchema {
    protected boolean required = false;

    public MapSchema required() {
        required = true;
        return null;
    }

    public abstract boolean isValid(Object value);
}

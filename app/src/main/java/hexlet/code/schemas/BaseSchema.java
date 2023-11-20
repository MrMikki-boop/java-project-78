package hexlet.code.schemas;

public abstract class BaseSchema {
    protected boolean required = false;

    public BaseSchema required() {
        required = true;
        return this;
    }

    public abstract boolean isValid(Object value);
}

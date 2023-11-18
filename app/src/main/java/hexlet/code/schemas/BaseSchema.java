package hexlet.code.schemas;

public abstract class BaseSchema {
    protected boolean required = false;

    public void required() {
        required = true;
    }

    public abstract boolean isValid(Object value);
}

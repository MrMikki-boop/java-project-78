package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {

    // Константы для определения различных проверок в схеме
    private static final String DATA_TYPE = "dataType";
    private static final String REQUIRED = "required";
    private static final String MIN_LENGTH = "minLength";
    private static final String CONTAINS = "contains";

    // Конструктор класса, который добавляет проверку на соответствие типу данных (String или null)
    public StringSchema() {
        addCheck(DATA_TYPE, value -> (value instanceof String) || value == null);
    }

    // Метод, добавляющий проверку на обязательность (должно быть не null и не пустой строкой)
    public StringSchema required() {
        addCheck(REQUIRED, value -> (value instanceof String) && !((String) value).isEmpty());
        return this;
    }

    // Метод, добавляющий проверку на минимальную длину строки
    public StringSchema minLength(int number) {
        addCheck(MIN_LENGTH, value -> (value == null) || (((String) value).length() >= number));
        return this;
    }

    // Метод, добавляющий проверку на наличие определенной подстроки
    public StringSchema contains(String string) {
        addCheck(CONTAINS, value -> (value != null) && ((String) value).contains(string));
        return this;
    }
}

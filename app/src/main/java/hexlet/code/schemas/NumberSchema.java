package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {

    // Константы для определения типа данных, обязательности, положительности и диапазона
    private static final String DATA_TYPE = "dataType";
    private static final String REQUIRED = "required";
    private static final String POSITIVE = "positive";
    private static final String RANGE = "range";

    // Переменные для хранения значений первого и последнего чисел диапазона
    private int firstNumber;
    private int lastNumber;

    // Конструктор класса NumberSchema
    public NumberSchema() {
        addCheck(DATA_TYPE, value -> (value instanceof Integer) || (value == null));
    }

    // Метод для установки обязательности значения
    public NumberSchema required() {
        addCheck(REQUIRED, value -> value instanceof Integer);
        return this;
    }

    // Метод для установки положительности значения
    public NumberSchema positive() {
        addCheck(POSITIVE, value -> (value == null) || ((int) value > 0));
        return this;
    }

    // Метод для установки диапазона значений
    public NumberSchema range(Integer first, Integer last) {
        firstNumber = first;
        lastNumber = last;
        addCheck(RANGE, value -> (value == null) || ((int) value >= firstNumber && (int) value <= lastNumber));
        return this;
    }

}

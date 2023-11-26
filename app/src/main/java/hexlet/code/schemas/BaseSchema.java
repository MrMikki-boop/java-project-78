package hexlet.code.schemas;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.Predicate;

public abstract class BaseSchema {

    // Переменная для хранения проверок в виде отображения (название проверки -> условие)
    protected Map<String, Predicate> checks;

    public BaseSchema() {
        // Инициализация переменной checks новым экземпляром LinkedHashMap для сохранения порядка добавления элементов
        checks = new LinkedHashMap<>();
    }

    // Метод для добавления проверки в отображение checks
    public void addCheck(String name, Predicate<Object> check) {
        checks.put(name, check);
    }

    // Метод для проверки валидности переданного значения
    public final boolean isValid(Object value) {

        // Перебор всех проверок в отображении checks
        for (Predicate validate : checks.values()) {
            // Если хотя бы одна проверка не пройдена, возвращается false
            if (!validate.test(value)) {
                return false;
            }
        }

        return true;
    }
}

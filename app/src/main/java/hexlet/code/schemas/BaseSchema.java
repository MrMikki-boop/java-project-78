package hexlet.code.schemas;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.function.Predicate;

/**
 * Абстрактный класс, представляющий базовую схему для валидации значений.
 */
public abstract class BaseSchema {

    /**
     * Переменная для хранения проверок в виде отображения (название проверки -> условие).
     */
    protected Map<String, Predicate> checks;

    /**
     * Конструктор. Инициализирует переменную checks новым экземпляром
     * LinkedHashMap для сохранения порядка добавления элементов.
     */
    public BaseSchema() {
        checks = new LinkedHashMap<>();
    }

    /**
     * Метод для добавления проверки в отображение checks.
     *
     * @param name  java-project-78
     * @param check условие проверки - объект типа Predicate<Object>,
     *              представляющий условие, которое должно быть выполнено для успешной валидации значения
     */
    public void addCheck(String name, Predicate<Object> check) {
        checks.put(name, check);
    }

    /**
     * Метод для проверки валидности переданного значения.
     *
     * @param value значение для валидации
     * @return true, если значение проходит все проверки, false в противном случае
     */
    public final boolean isValid(Object value) {
        for (Predicate validate : checks.values()) {
            if (!validate.test(value)) {
                return false;
            }
        }
        return true;
    }
}

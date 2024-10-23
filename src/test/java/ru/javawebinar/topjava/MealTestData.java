package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class MealTestData {
    public static final int MEAL1_ID = START_SEQ + 3;
    public static final int ADMIN_MEAL1_ID = START_SEQ + 10;
    public static final int NOT_FOUND = 10;
    public static final Meal meal1 = new Meal(MEAL1_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal meal2 = new Meal(MEAL1_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal meal3 = new Meal(MEAL1_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal meal4 = new Meal(MEAL1_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal meal5 = new Meal(MEAL1_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal meal6 = new Meal(MEAL1_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal meal7 = new Meal(MEAL1_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL1_ID, LocalDateTime.of(2020, Month.JANUARY, 31, 14, 0), "Админ ланч", 510);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL1_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 31, 21, 0), "Админ ужин", 1500);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.MARCH, 2, 10, 0),  "description", DEFAULT_CALORIES_PER_DAY);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, meal1.getDateTime().plus(1, ChronoUnit.MINUTES), "updated", 777);
    }

    public static Meal getDuplicateByDateTime() {
        return new Meal(null, meal1.getDateTime(),  "duplicate", DEFAULT_CALORIES_PER_DAY);
    }

    public static void assertMatch(Meal actual, Meal expected, String... ignoreFields) {
        assertMatch(Collections.singletonList(actual), Collections.singletonList(expected), ignoreFields);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected, String... ignoreFields) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields(ignoreFields).isEqualTo(expected);
    }
}

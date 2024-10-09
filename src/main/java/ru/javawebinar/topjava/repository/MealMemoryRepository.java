package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMemoryRepository implements MealRepository {

    private final AtomicInteger atomicInt = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    {
        Meal meal = new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        save(meal);
        Meal meal1 = new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        save(meal1);
        Meal meal2 = new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        save(meal2);
        Meal meal3 = new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        save(meal3);
        Meal meal4 = new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        save(meal4);
        Meal meal5 = new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        save(meal5);
        Meal meal6 = new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
        save(meal6);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getById(int id) {
        return meals.get(id);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            Meal created = new Meal(atomicInt.incrementAndGet(),
                    meal.getDateTime(), meal.getDescription(), meal.getCalories());
            meals.put(created.getId(), created);
            return created;
        }
        return meals.computeIfPresent(meal.getId(), (key, value) -> meal);
    }

    @Override
    public boolean delete(int id) {
        return meals.remove(id) != null;
    }
}

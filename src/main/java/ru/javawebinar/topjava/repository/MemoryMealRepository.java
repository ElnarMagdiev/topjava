package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealRepository implements MealRepository {

    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    {
        List<Meal> meals = Arrays.asList(
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(null,LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(null,LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(null,LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(null,LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(null,LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(null,LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        meals.forEach(this::save);
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
            Meal created = new Meal(idCounter.incrementAndGet(),
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

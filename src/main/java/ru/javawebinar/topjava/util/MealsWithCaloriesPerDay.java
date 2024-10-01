package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

public final class MealsWithCaloriesPerDay {
    private List<UserMeal> meals;
    private Integer caloriesPerDay;

    public MealsWithCaloriesPerDay(List<UserMeal> meals, Integer caloriesPerDay) {
        this.meals = meals;
        this.caloriesPerDay = caloriesPerDay;
    }

    public List<UserMeal> getMeals() {
        return meals;
    }

    public Integer getCaloriesPerDay() {
        return caloriesPerDay;
    }
}

package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

       //System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDate = new HashMap<>();
        for (UserMeal meal: meals) {
            LocalDate mealLocalDate = meal.getDateTime().toLocalDate();
            caloriesPerDate.merge(mealLocalDate, meal.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> resultList = new ArrayList<>();
        for (UserMeal meal: meals) {
            LocalDateTime mealLocalDateTime = meal.getDateTime();
            if (TimeUtil.isBetweenHalfOpen(mealLocalDateTime.toLocalTime(), startTime, endTime)) {
                resultList.add(new UserMealWithExcess(mealLocalDateTime, meal.getDescription(), meal.getCalories(), caloriesPerDate.get(mealLocalDateTime.toLocalDate()) > caloriesPerDay));
            }
        }
        return resultList;
    }

    public static List<UserMealWithExcess> filteredByCyclesOptional2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDate = new HashMap<>();
        List<UserMealWithExcess> resultList = new ArrayList<>();
        Predicate<Boolean> predicate = bool -> true;
        for (UserMeal meal: meals) {
            caloriesPerDate.merge(meal.getDate(), meal.getCalories(), Integer::sum);
            if (TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime)) {
                predicate = predicate.and(bool -> resultList.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesPerDate.get(meal.getDate()) > caloriesPerDay)));
            }
        }
        predicate.test(true);
        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDate = meals.stream().collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesPerDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> filteredByStreamsOptional2(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate))
                .values()
                .stream()
                .map(val -> new MealsWithCaloriesPerDay(val, val.stream().mapToInt(UserMeal::getCalories).sum()))
                .flatMap(mws -> mws.getMeals().stream()
                        .filter(m -> TimeUtil.isBetweenHalfOpen(m.getTime(), startTime, endTime))
                        .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), mws.getCaloriesPerDay() > caloriesPerDay)))
                .collect(Collectors.toList());
    }
}

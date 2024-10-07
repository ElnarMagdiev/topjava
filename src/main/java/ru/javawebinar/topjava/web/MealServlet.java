package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<Meal> meals = MealsUtil.getMealList();
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );
        List<MealTo> mealTos = meals.stream()
                        .map(meal -> MealsUtil.createTo(meal, caloriesSumByDate.get(meal.getDate()) > MealsUtil.CALORIES_PER_DAY))
                        .sorted(Comparator.comparing(MealTo::getDateTime))
                        .collect(Collectors.toList());
        req.setAttribute("list", mealTos);
        req.setAttribute("formatter", formatter);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}

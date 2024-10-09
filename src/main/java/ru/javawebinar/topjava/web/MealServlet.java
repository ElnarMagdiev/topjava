package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");

    private MealRepository mealRepository;

    @Override
    public void init() {
        mealRepository = new MemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("GET request");

        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }
        switch (action) {
            case "edit":
                edit(req, resp);
                break;
            case "add":
                add(req, resp);
                break;
            case "delete":
                delete(req, resp);
                break;
            default:
                list(req, resp);
        }
    }

    private void list(HttpServletRequest req,
                      HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealTo> mealTos = MealsUtil.filteredByStreams(mealRepository.getAll(),
                LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);

        req.setAttribute("list", mealTos);
        req.setAttribute("formatter", FORMATTER);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    private void add(HttpServletRequest req,
                     HttpServletResponse resp) throws ServletException, IOException {
        log.debug("add meal");
        req.getRequestDispatcher("mealEditForm.jsp").forward(req, resp);
    }

    private void edit(HttpServletRequest req,
                      HttpServletResponse resp) throws ServletException, IOException {
        log.debug("edit meal");
        String id = req.getParameter("id");
        req.setAttribute("meal", mealRepository.getById(Integer.parseInt(id)));
        req.getRequestDispatcher("mealEditForm.jsp").forward(req, resp);
    }

    private void delete(HttpServletRequest req,
                        HttpServletResponse resp) throws IOException {
        log.debug("delete meal");
        String id = req.getParameter("id");
        mealRepository.delete(Integer.parseInt(id));
        log.debug("meal deleted id: {}", id);
        resp.sendRedirect("meals");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("POST request");
        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            create(req, resp);
        } else {
            update(req, resp);
        }
    }

    private void create(HttpServletRequest req,
                        HttpServletResponse resp) throws IOException {
        log.debug("create meal");
        Meal mealFromReq = fromRequest(req);
        Meal created = mealRepository.save(mealFromReq);
        log.debug("meal created {}", created);
        resp.sendRedirect("meals");
    }

    private void update(HttpServletRequest req,
                        HttpServletResponse resp) throws IOException {
        log.debug("update meal");

        Meal mealFromReq = fromRequest(req);
        Meal updated = mealRepository.save(mealFromReq);
        log.debug("meal updated {} ", updated);
        resp.sendRedirect("meals");
    }

    private static Meal fromRequest(HttpServletRequest req) {
        String id = req.getParameter("id");
        String dateTime = req.getParameter("dateTime");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");
        return new Meal(
                id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(dateTime),
                description,
                Integer.parseInt(calories)
        );
    }
}

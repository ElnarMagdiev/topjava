package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.getAll(USER_ID).get(0);
        assertMatch(meal, meal3, "id");
    }

    @Test
    public void delete() {
        Meal created = service.create(getNew(), USER_ID);
        Integer id = created.getId();

        service.delete(id, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(id, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        Meal meal = service.getAll(USER_ID).get(0);

        List<Meal> all = service.getAll(USER_ID).stream()
                .filter(dt -> dt.getDate().isEqual(meal.getDate()))
                .collect(Collectors.toList());

        assertMatch(all, Arrays.asList(meal3, meal2, meal1), "id");
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, Arrays.asList(meal3, meal2, meal1), "id");
    }

    @Test
    public void update() {
        Meal meal = getUpdated(service.getAll(USER_ID).get(0));
        service.update(meal, USER_ID);
        assertMatch(service.get(meal.getId(), USER_ID), meal);
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void getNotOwn() {
        Meal userMeal = service.getAll(USER_ID).get(0);
        assertThrows(NotFoundException.class, () -> service.get(userMeal.getId(), ADMIN_ID));
    }

    @Test
    public void deleteNotOwn() {
        Meal userMeal = service.getAll(USER_ID).get(0);
        assertThrows(NotFoundException.class, () -> service.delete(userMeal.getId(), ADMIN_ID));
    }

    @Test
    public void updateNotOwn() {
        Meal userMeal = service.getAll(USER_ID).get(0);
        assertThrows(NotFoundException.class, () -> service.update(userMeal, ADMIN_ID));
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(getDuplicateByDate(), USER_ID));
    }
}
package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.UserTestData.USER_MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getByIdAndUserId() {
        Meal meal = service.getByIdAndUserId(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        User user = meal.getUser();
        MEAL_MATCHER.assertMatch(meal, MealTestData.meal1);
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }

    @Test
    public void getNotOwnByIdAndUserId() {
        thrown.expect(NotFoundException.class);
        service.getByIdAndUserId(MealTestData.MEAL1_ID, UserTestData.ADMIN_ID);
    }

    @Test
    public void getNotFoundByIdAndUserId() {
        thrown.expect(NotFoundException.class);
        service.getByIdAndUserId(1, UserTestData.USER_ID);
    }

}

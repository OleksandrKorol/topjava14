package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL1_ID, USER_ID);
        assertMatch(MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void NotFoundGet() {
        Meal meal = service.get(MEAL1_ID, USER_ID + 3);
    }

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test(expected = NotFoundException.class)
    public void NotFoundDelete() {
        service.delete(MEAL1_ID, USER_ID + 3);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("new24");
        updated.setCalories(44);
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL1_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("new24");
        updated.setCalories(44);
        service.update(updated, USER_ID + 3);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2018, Month.MAY, 31, 20, 0), "NEW", 510);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test(expected = DuplicateKeyException.class)
    public void duplicteDateTimeCreate() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "NEW", 510);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> meals = service.getBetweenDateTimes(
                LocalDateTime.of(2015, 05, 30, 10, 0),
                LocalDateTime.of(2015, 05, 30, 14, 0),
                USER_ID
        );
        assertMatch(meals, MEAL2, MEAL1);
    }
}
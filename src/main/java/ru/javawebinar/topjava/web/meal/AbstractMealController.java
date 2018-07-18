package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class AbstractMealController {
    private static final Logger log = getLogger(AbstractMealController.class);

    @Autowired
    private MealService service;

    public Collection<Meal> getAll() {
        log.info("getAll");
        return service.getAll(authUserId());
    }

    public Meal get(int id) {
        int userId = authUserId();
        log.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = authUserId();
        log.info("create meal {} for User {}", meal, userId);
        checkNew(meal);
        return service.save(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = authUserId();
        log.info("update meal {} with id={} for User {}", meal, id, userId);
        assureIdConsistent(meal, id);
        service.save(meal, userId);
    }

    public Collection<MealWithExceed> getBetween(LocalDate sd, LocalTime st, LocalDate ed, LocalTime et) {
        log.info("get between by date fo ({} {}) for ({} {})", sd, st, ed, et);
        Collection<Meal> meals = service.getBetweenDates(authUserId(),
                sd != null ? sd : DateTimeUtil.MIN_DATE,
                ed != null ? ed : DateTimeUtil.MAX_DATE);

        return MealsUtil.getFilteredWithExceeded(meals, DEFAULT_CALORIES_PER_DAY,
                st != null ? st : LocalTime.MIN,
                et != null ? et : LocalTime.MAX
        );
    }
}

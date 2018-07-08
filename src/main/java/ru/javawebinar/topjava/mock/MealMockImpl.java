package ru.javawebinar.topjava.mock;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealMockImpl implements MealMock {
    private ConcurrentMap<Integer, Meal> mealsMap = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger();

    {
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public void add(Meal meal) {
        if (meal.isNew()) {
            int id = count.getAndIncrement();
            meal.setId(id);
            mealsMap.put(id, meal);
        } else {
            mealsMap.put(meal.getId(), meal);
        }

    }

    @Override
    public void remove(int id) {
        mealsMap.remove(id);
    }

    @Override
    public Meal get(int id) {
        return mealsMap.get(id);
    }

    @Override
    public void update(Meal meal) {
        add(meal);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(mealsMap.values());
    }
}

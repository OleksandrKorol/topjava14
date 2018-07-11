package ru.javawebinar.topjava.mock;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MockMealRepository {
    void save(Meal meal);

    void remove(int id);

    Meal get(int id);

    void update(Meal meal);

    Collection<Meal> getAll();
}

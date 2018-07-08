package ru.javawebinar.topjava.mock;

import ru.javawebinar.topjava.model.Meal;

public interface MealMock {
    void add(Meal meal);

    void remove(int id);

    Meal get(int id);

    void update(Meal meal);
}

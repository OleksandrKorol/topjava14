package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

public interface MealService {
    Meal save(Meal meal, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    Collection<Meal> getAll(int userId) throws NotFoundException;

    default Collection<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate){
        return getBetween(userId, LocalDateTime.of(startDate, LocalTime.MIN),
                LocalDateTime.of(endDate, LocalTime.MAX));
    }

    Collection<Meal> getBetween(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
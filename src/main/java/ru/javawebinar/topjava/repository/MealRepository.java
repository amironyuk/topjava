package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    // null if updated meal does not belong to user or not found when updated
    Meal save(Meal meal, int userId);

    // false if meal does not belong to user or not found
    boolean delete(int id, int userId);

    // null if meal does not belong to user or not found
    Meal get(int id, int userId);

    // ORDERED dateTime desc
    List<Meal> getAll(int userId);

    // ORDERED dateTime desc
    List<Meal> get(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId);
}

package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private static final Sort SORT_DATE_TIME = Sort.by(Sort.Direction.DESC, "dateTime");

    private final CrudMealRepository crudMealRepo;
    private final CrudUserRepository crudUserRepo;

    public DataJpaMealRepository(CrudMealRepository crudMealRepo, CrudUserRepository crudUserRepo) {
        this.crudMealRepo = crudMealRepo;
        this.crudUserRepo = crudUserRepo;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.id(), userId) == null) {
            return null;
        }
        meal.setUser(crudUserRepo.getOne(userId));
        return crudMealRepo.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudMealRepo.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = crudMealRepo.getByIdAndUserId(id, userId);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudMealRepo.getAllByUserId(userId, SORT_DATE_TIME);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudMealRepo.getBetweenHalfOpen(startDateTime, endDateTime, userId);
    }
}

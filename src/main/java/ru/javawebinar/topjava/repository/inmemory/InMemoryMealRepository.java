package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetween;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, MealsUtil.DEFAULT_USER_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} for user={}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            getUserMeals(userId).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but only if present in storage
        return getUserMeals(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} for user={}", id, userId);
        return getUserMeals(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} for user={}", id, userId);
        return getUserMeals(userId).get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll for user={}", userId);
        return get(meal -> true, userId);
    }

    @Override
    public List<Meal> get(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        log.info("get filtered by date=[{},{}] and time=[{},{}) for user={}", startDate, endDate, startTime, endTime, userId);
        Predicate<Meal> condition = (meal -> isBetween(meal.getDate(), startDate, endDate)
                && isBetweenHalfOpen(meal.getTime(), startTime, endTime));
        return get(condition, userId);
    }

    private List<Meal> get(Predicate<Meal> condition, int userId) {
        return getUserMeals(userId).values().stream()
                .filter(condition)
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }

    private Map<Integer, Meal> getUserMeals(int userId) {
        return repository.computeIfAbsent(userId, x -> new ConcurrentHashMap<>());
    }
}

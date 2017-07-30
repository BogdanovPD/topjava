package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(m -> {save(1, m);});
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int userId, int id) {
        Meal meal = get(userId, id);
        if (meal == null){
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal meal = repository.get(id);
        if (meal.getUserId() == userId){
            return meal;
        }
        return  null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        List<Meal> mealList = repository.values().stream()
                .filter(v -> (v.getUserId() == userId)).collect(Collectors.toList());
        if (mealList != null){
            return mealList.stream().sorted(Comparator.comparing(Meal::getDateTime))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}


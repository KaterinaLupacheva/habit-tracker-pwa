package io.ramonak.habitTracker.repository;

import io.ramonak.habitTracker.entity.Habit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitRepository extends CrudRepository<Habit, Integer> {

    List<Habit> findAllByOrderByName();
}

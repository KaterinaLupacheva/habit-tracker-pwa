package io.ramonak.habitTracker.repository;

import io.ramonak.habitTracker.entity.Achievement;
import io.ramonak.habitTracker.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AchievementRepository extends CrudRepository<Achievement, Long> {

    List<Achievement> findAllByDateBetweenAndUserIs(LocalDate start, LocalDate end, User user);
    List<Achievement> findByHabitId(Integer habitId);
}

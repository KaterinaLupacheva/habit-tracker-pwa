package io.ramonak.habitTracker.service;

import io.ramonak.habitTracker.UI.DTO.HabitDTO;
import io.ramonak.habitTracker.entity.Achievement;
import io.ramonak.habitTracker.entity.Habit;
import io.ramonak.habitTracker.repository.AchievementRepository;
import io.ramonak.habitTracker.repository.HabitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HabitService {

    private final HabitRepository habitRepository;
    private final AchievementRepository achievementRepository;

    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    public void deleteHabit(Habit habit) {
        List<Achievement> achievements = achievementRepository.findByHabitId(habit.getId());
        achievementRepository.deleteAll(achievements);
        habitRepository.findById(habit.getId()).ifPresent(habitRepository::delete);
    }
}

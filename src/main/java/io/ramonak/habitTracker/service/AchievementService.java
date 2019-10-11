package io.ramonak.habitTracker.service;

import io.ramonak.habitTracker.UI.DTO.AchievementDTO;
import io.ramonak.habitTracker.UI.DTO.HabitDTO;
import io.ramonak.habitTracker.entity.Achievement;
import io.ramonak.habitTracker.entity.Habit;
import io.ramonak.habitTracker.repository.AchievementRepository;
import io.ramonak.habitTracker.repository.HabitRepository;
import io.ramonak.habitTracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public Achievement updateAchievement(Long achievementId,
                                         Integer habitId,
                                         Achievement updatedAchievement,
                                         String userEmail) {
        try {
            Achievement achievement = achievementRepository.findById(achievementId)
                    .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Achievement " + achievementId + " not found"));
            Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new ResponseStatusException
                    (HttpStatus.NOT_FOUND, "Habit " + habitId + " not found"));
            achievement.setStatus(updatedAchievement.getStatus());
            achievement.setDate(updatedAchievement.getDate());
            achievement.setHabit(habit);
            achievement.setUser(userRepository.findByEmailIgnoreCase(userEmail));
            return achievementRepository.save(achievement);
        } catch (ResponseStatusException ex) {
            ex.getStatus();
            ex.printStackTrace();
            return null;
        }
    }

    public List<Achievement> getAllBetweenDates(LocalDate start, LocalDate end, String userEmail) {
        return achievementRepository.findAllByDateBetweenAndUserIs(start, end,
                userRepository.findByEmailIgnoreCase(userEmail));
    }

    public Achievement saveAchievementToHabit(Integer habitId, Achievement achievement, String userEmail) {
        return habitRepository.findById(habitId).map(habit -> {
            achievement.setHabit(habit);
            achievement.setUser(userRepository.findByEmailIgnoreCase(userEmail));
            return achievementRepository.save(achievement);
        }).orElseThrow(() -> new ResponseStatusException
                (HttpStatus.NOT_FOUND, "Habit " + habitId + " not found"));
    }
}

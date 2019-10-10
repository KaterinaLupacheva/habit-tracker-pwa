package io.ramonak.habitTracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class HabitForGrid {

    private Integer habitId;
    private String name;
    private String description;
    private AchievementForGrid achievementMonday;
    private AchievementForGrid achievementTuesday;
    private AchievementForGrid achievementWednesday;
    private AchievementForGrid achievementThursday;
    private AchievementForGrid achievementFriday;
    private AchievementForGrid achievementSaturday;
    private AchievementForGrid achievementSunday;

    public HabitForGrid(Habit habit, List<Achievement> achievements) {
        this.habitId = habit.getId();
        this.name = habit.getName();
        this.description = habit.getDescription();
        for (Achievement achievement : achievements) {
            switch (achievement.getDate().getDayOfWeek().getValue()) {
                case 1:
                    this.achievementMonday = new AchievementForGrid(achievement);
                    break;
                case 2:
                    this.achievementTuesday = new AchievementForGrid(achievement);
                    break;
                case 3:
                    this.achievementWednesday = new AchievementForGrid(achievement);
                    break;
                case 4:
                    this.achievementThursday = new AchievementForGrid(achievement);
                    break;
                case 5:
                    this.achievementFriday = new AchievementForGrid(achievement);
                    break;
                case 6:
                    this.achievementSaturday = new AchievementForGrid(achievement);
                    break;
                case 7:
                    this.achievementSunday = new AchievementForGrid(achievement);
                    break;
            }
        }
    }
}

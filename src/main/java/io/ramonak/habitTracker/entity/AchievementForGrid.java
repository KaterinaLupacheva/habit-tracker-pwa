package io.ramonak.habitTracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class AchievementForGrid {

    private Long id;
    private LocalDate date;
    private int status;

    public AchievementForGrid(Achievement achievement) {
        this.id = achievement.getId();
        this.date = achievement.getDate();
        this.status = achievement.getStatus();
    }
}

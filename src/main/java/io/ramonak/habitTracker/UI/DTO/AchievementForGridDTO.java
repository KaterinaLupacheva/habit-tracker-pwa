package io.ramonak.habitTracker.UI.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class AchievementForGridDTO {

    private Long id;
    private LocalDate date;
    private int status;

    public AchievementForGridDTO(AchievementDTO achievementDTO) {
        this.id = achievementDTO.getId();
        this.date = achievementDTO.getDate();
        this.status = achievementDTO.getStatus();
    }
}

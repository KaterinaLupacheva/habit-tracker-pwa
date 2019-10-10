package io.ramonak.habitTracker.UI.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AchievementDTO {

    private Long id;
    private int status;
    private LocalDate date;
    private HabitDTO habit;

    public AchievementDTO(DateDTO dateDTO) {
        this.date = dateDTO.getDay();
    }

    public AchievementDTO(AchievementForGridDTO achievementForGridDTO) {
        this.id = achievementForGridDTO.getId();
        this.setStatus(achievementForGridDTO.getStatus());
        this.date = achievementForGridDTO.getDate();
    }
}

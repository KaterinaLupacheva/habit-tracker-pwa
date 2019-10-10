package io.ramonak.habitTracker.UI.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class HabitForGridDTO {

    private Integer habitId;
    private String name;
    private String description;
    private AchievementForGridDTO achievementMonday;
    private AchievementForGridDTO achievementTuesday;
    private AchievementForGridDTO achievementWednesday;
    private AchievementForGridDTO achievementThursday;
    private AchievementForGridDTO achievementFriday;
    private AchievementForGridDTO achievementSaturday;
    private AchievementForGridDTO achievementSunday;

    public HabitForGridDTO(HabitDTO habitDTO, List<AchievementDTO> achievementDTOs) {
        this.habitId = habitDTO.getId();
        this.name = habitDTO.getName();
        this.description = habitDTO.getDescription();
        for (AchievementDTO tempAchievementDTO : achievementDTOs) {
            switch (tempAchievementDTO.getDate().getDayOfWeek().getValue()) {
                case 1:
                    this.achievementMonday = new AchievementForGridDTO(tempAchievementDTO);
                    break;
                case 2:
                    this.achievementTuesday = new AchievementForGridDTO(tempAchievementDTO);
                    break;
                case 3:
                    this.achievementWednesday = new AchievementForGridDTO(tempAchievementDTO);
                    break;
                case 4:
                    this.achievementThursday = new AchievementForGridDTO(tempAchievementDTO);
                    break;
                case 5:
                    this.achievementFriday = new AchievementForGridDTO(tempAchievementDTO);
                    break;
                case 6:
                    this.achievementSaturday = new AchievementForGridDTO(tempAchievementDTO);
                    break;
                case 7:
                    this.achievementSunday = new AchievementForGridDTO(tempAchievementDTO);
                    break;
            }
        }
    }
}

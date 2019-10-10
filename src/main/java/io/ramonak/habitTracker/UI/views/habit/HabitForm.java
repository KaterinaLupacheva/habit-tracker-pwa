package io.ramonak.habitTracker.UI.views.habit;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import io.ramonak.habitTracker.UI.DTO.*;
import io.ramonak.habitTracker.UI.component.GridHeader;
import io.ramonak.habitTracker.entity.Achievement;
import io.ramonak.habitTracker.entity.Habit;
import io.ramonak.habitTracker.entity.HabitForGrid;
import io.ramonak.habitTracker.service.AchievementService;
import org.claspina.confirmdialog.ConfirmDialog;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

class HabitForm extends VerticalLayout {

    private final TextField name;
    private final TextArea description;
    private final CheckboxGroup<DayOfWeek> plan = new CheckboxGroup<>();

    private final Binder<Habit> binder;
    private final HabitView habitView;
//    private HabitDTO habitDTO;
    private Habit habit;
    private final AchievementService achievementService;
    private Map<LocalDate, Integer> achievements;

    public HabitForm(HabitView habitView, AchievementService achievementService) {
        this.habitView = habitView;
        this.achievementService = achievementService;
        setSizeUndefined();
        binder = new Binder<>(Habit.class);

        name = new TextField("Habit");
        name.setPlaceholder("Enter your habit");
        name.setAutofocus(true);

        description = new TextArea("Description (Optional)");
        description.setPlaceholder("Describe your habit");
        Label label = new Label("Plan (Optional)");
        plan.setDataProvider(DataProvider.ofItems(DayOfWeek.values()));

        Button saveButton = new Button("SAVE");
        saveButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        saveButton.addClickShortcut(Key.ENTER);
        saveButton.addClickListener(e -> save());
        Button deleteButton = new Button("DELETE");
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(e-> delete());
        Button cancelButton = new Button("CANCEL");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        cancelButton.addClickListener(e-> cancel());
        cancelButton.addClickShortcut(Key.ESCAPE);
        HorizontalLayout buttons = new HorizontalLayout(saveButton, deleteButton, cancelButton);
        add(name, description, label, plan, buttons);
        setBinder();
    }

    private void setBinder() {
        binder.forField(name).bind(Habit::getName, Habit::setName);
        binder.forField(description).bind(Habit::getDescription, Habit::setDescription);
        binder.forField(plan).bind(Habit::getDaysOfWeek, Habit::setDaysOfWeek);
    }

    public void setHabit(HabitForGrid habitForGrid) {
        if (habitForGrid.getHabitId()!=null) {
            this.habit = new Habit(habitForGrid);
            achievements = new HashMap<>();
            achievements.put(habitForGrid.getAchievementMonday().getDate(), habitForGrid.getAchievementMonday().getStatus());
            achievements.put(habitForGrid.getAchievementTuesday().getDate(), habitForGrid.getAchievementTuesday().getStatus());
            achievements.put(habitForGrid.getAchievementWednesday().getDate(), habitForGrid.getAchievementWednesday().getStatus());
            achievements.put(habitForGrid.getAchievementThursday().getDate(), habitForGrid.getAchievementThursday().getStatus());
            achievements.put(habitForGrid.getAchievementFriday().getDate(), habitForGrid.getAchievementFriday().getStatus());
            achievements.put(habitForGrid.getAchievementSaturday().getDate(), habitForGrid.getAchievementSaturday().getStatus());
            achievements.put(habitForGrid.getAchievementSunday().getDate(), habitForGrid.getAchievementSunday().getStatus());
        } else {
            this.habit = new Habit();
        }
        binder.setBean(habit);
        setVisible(true);
    }

    private void save() {
        habit = habitView.getHabitService().createHabit(this.habit);
        Set<DayOfWeek> selectedItems = plan.getSelectedItems();
        GridHeader gridHeader = new GridHeader();
        gridHeader.setupHeader(habitView.getToday());
        List<DateDTO> header = gridHeader.getHeader();
        for (DateDTO tempDate : header) {
            Achievement achievement = new Achievement(tempDate);
            int value = tempDate.getDow().getValue();
            Integer status = 0;
            if (achievements!=null) {
                status = achievements.get(tempDate.getDay());
            }
            for (DayOfWeek selectedItem : selectedItems) {
                Integer dow = selectedItem.getDow();
                if (dow == value) {
                    if (status.equals(11) || status.equals(12) || status.equals(1)) {
                        achievement.setStatus(status);
                    } else {
                        achievement.setStatus(10);
                    }
                } else {
                    if (status.equals(10)) {
                        achievement.setStatus(0);
                    }
                }
            }
            achievementService.saveAchievementToHabit(this.habit.getId(), achievement);
        }
        habitView.updateList(habitView.getToday());
        setVisible(false);
        habitView.getGrid().setVisible(true);
        habitView.getAddHabit().setVisible(true);
    }

    private void delete() {
        ConfirmDialog.createQuestion()
                .withMessage("Delete habit?")
                .withOkButton(()-> {
                    habitView.getHabitService().deleteHabit(habit);
                    habitView.updateList(habitView.getToday());
                    setVisible(false);
                    habitView.getAddHabit().setVisible(true);
                }).withCancelButton(()-> {
            setVisible(false);
            habitView.getGrid().setVisible(true);
            habitView.getAddHabit().setVisible(true);
        }).open();
    }

    private void cancel() {
        setVisible(false);
        habitView.getGrid().setVisible(true);
        habitView.getGrid().asSingleSelect().clear();
        habitView.getGrid().getDataProvider().refreshAll();
        habitView.getAddHabit().setVisible(true);
    }
}

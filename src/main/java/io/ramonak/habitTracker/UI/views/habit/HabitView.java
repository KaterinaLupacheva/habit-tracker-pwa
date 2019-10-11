package io.ramonak.habitTracker.UI.views.habit;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import io.ramonak.habitTracker.MainView;
import io.ramonak.habitTracker.UI.DTO.AchievementDTO;
import io.ramonak.habitTracker.UI.DTO.HabitDTO;
import io.ramonak.habitTracker.UI.DTO.HabitForGridDTO;
import io.ramonak.habitTracker.UI.component.DateCell;
import io.ramonak.habitTracker.UI.component.GridHeader;
import io.ramonak.habitTracker.UI.views.utils.DateUtils;
import io.ramonak.habitTracker.entity.Achievement;
import io.ramonak.habitTracker.entity.Habit;
import io.ramonak.habitTracker.entity.HabitForGrid;
import io.ramonak.habitTracker.service.AchievementService;
import io.ramonak.habitTracker.service.HabitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = "habit", layout = MainView.class)
@PageTitle("Habits")
public class HabitView extends VerticalLayout {

    private final Grid<HabitForGrid> grid;
    private final HabitService habitService;
    private final AchievementService achievementService;
    private final HabitForm habitForm;
    private final GridHeader gridHeader;
    private LocalDate today;
    private boolean isIconClicked;
    private final Button addHabit;
    private String userEmail = "";

    public HabitView(HabitService habitService, AchievementService achievementService) {
        setSizeFull();
        this.habitService = habitService;
        this.achievementService = achievementService;
        habitForm = new HabitForm(this, achievementService);
        habitForm.setVisible(false);
        Span username = new Span();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username.setText(authentication.getName());
            userEmail = authentication.getName();
        }
        Button logout = new Button("Logout");
        logout.addClickListener(e -> {
            VaadinSession.getCurrent().getSession().invalidate();
            UI.getCurrent().getPage().executeJs("window.location.href=''");
                });
        HorizontalLayout user = new HorizontalLayout(username, logout);
        user.setWidthFull();
        user.setJustifyContentMode(JustifyContentMode.END);
        user.setVerticalComponentAlignment(Alignment.CENTER, username, logout);
        grid = new Grid<>(HabitForGrid.class);
        grid.addThemeNames("column-borders", "wrap-cell-content");
        gridHeader = new GridHeader();
        today = LocalDate.now();

        updateList(today);
        setGridHeader(setupGrid());
        setTodayColumn();

        grid.asSingleSelect().addValueChangeListener(event -> {
            if (isIconClicked) {
                habitForm.setVisible(false);
                grid.asSingleSelect().clear();
            } else if (event.getValue() == null) {
                habitForm.setVisible(false);
            } else {
                habitForm.setHabit(event.getValue());
            }
            isIconClicked = false;
        });

        addHabit = new Button("ADD");
        addHabit.addClickListener(e -> {
            grid.asSingleSelect().clear();
            habitForm.setHabit(new HabitForGrid());
            grid.setVisible(false);
            addHabit.setVisible(false);
        });

        Button nextWeek = new Button("Next Week ►");
        Button prevWeek = new Button("◄ Prev Week");

        prevWeek.addClickListener(e -> {
            today = today.minusDays(7L);
            updateGrid(addHabit);
        });

        nextWeek.addClickListener(e -> {
           today = today.plusDays(7);
            updateGrid(addHabit);
        });

        HorizontalLayout weekNavigationButtons = new HorizontalLayout(prevWeek, nextWeek);
        weekNavigationButtons.setWidthFull();
        nextWeek.getStyle().set("margin-left", "auto");

        add(user, weekNavigationButtons, grid, addHabit, habitForm);
    }

    private void updateGrid(Button addHabit) {
        updateList(today);
        List<Grid.Column<HabitForGrid>> columns = grid.getColumns();
        List<Grid.Column<HabitForGrid>> subList = columns.subList(1, 8);
        setGridHeader(subList);
        setTodayColumn();
        if (today.isBefore(LocalDate.now())) {
            addHabit.setVisible(false);
        } else {
            addHabit.setVisible(true);
        }
    }

    private List<Grid.Column<HabitForGrid>> setupGrid() {
        List<Grid.Column<HabitForGrid>> columns = new ArrayList<>();
        grid.removeAllColumns();
        grid.addColumn(HabitForGrid::getName).setHeader("Habit").setFlexGrow(5);

        columns.add(grid.addComponentColumn(item -> createImage(item, 1)));
        columns.add(grid.addComponentColumn(item -> createImage(item, 2)));
        columns.add(grid.addComponentColumn(item -> createImage(item, 3)));
        columns.add(grid.addComponentColumn(item -> createImage(item, 4)));
        columns.add(grid.addComponentColumn(item -> createImage(item, 5)));
        columns.add(grid.addComponentColumn(item -> createImage(item, 6)));
        columns.add(grid.addComponentColumn(item -> createImage(item, 7)));
        return columns;
    }

    private void setGridHeader(List<Grid.Column<HabitForGrid>> columns) {
        List<DateCell> listDateCells = gridHeader.setupHeader(today);
        int i = 0;
        for (Grid.Column<HabitForGrid> tempColumn : columns) {
            tempColumn.setHeader(listDateCells.get(i)).setTextAlign(ColumnTextAlign.CENTER);
            i++;
        }
    }

    private void setTodayColumn() {
        int dow = LocalDate.now().getDayOfWeek().getValue();
        if (today.equals(LocalDate.now())) {
            grid.getColumns().get(dow).setFlexGrow(4);
        } else {
            grid.getColumns().get(dow).setFlexGrow(1);
        }
    }

    private Image createImage(HabitForGrid item, Integer dayOfWeek) {
        int status;
        if (dayOfWeek==1) {
            status = item.getAchievementMonday().getStatus();
        } else if (dayOfWeek == 2) {
            status = item.getAchievementTuesday().getStatus();
        } else if (dayOfWeek == 3) {
            status = item.getAchievementWednesday().getStatus();
        } else if (dayOfWeek == 4) {
            status = item.getAchievementThursday().getStatus();
        } else if (dayOfWeek == 5) {
            status = item.getAchievementFriday().getStatus();
        } else if (dayOfWeek == 6) {
            status = item.getAchievementSaturday().getStatus();
        } else if (dayOfWeek == 7) {
            status = item.getAchievementSunday().getStatus();
        } else {
            status = 0;
        }
        if(status==0) {
            Image image = new Image("frontend/images/check-box-empty.png", "not planned");
            image.setMaxHeight("64px");
            image.setMaxWidth("64px");
            return updateImage(item, image, dayOfWeek);
        } else if (status==10) {
            Image image = new Image("frontend/images/plan.png", "planned");
            image.setMaxHeight("64px");
            image.setMaxWidth("64px");
            return updateImage(item, image, dayOfWeek);
        } else if (status==1 || status == 11) {
            Image image = new Image("frontend/images/done.png", "done");
            image.setMaxHeight("64px");
            image.setMaxWidth("64px");
            return updateImage(item, image, dayOfWeek);
        } else if (status==12) {
            Image image = new Image("frontend/images/not-done.png", "not done");
            image.setMaxHeight("64px");
            image.setMaxWidth("64px");
            return updateImage(item, image, dayOfWeek);
        } else {
            return new Image("","");
        }
    }

    private Image updateImage(HabitForGrid item, Image image, Integer dayOfWeek) {
        image.addClickListener(e -> clickListenerForIcon(item, dayOfWeek));
        return image;
    }

    private void clickListenerForIcon(HabitForGrid item, Integer dayOfWeek) {
        if (dayOfWeek==1) {
            //status 0 - not planned, 1 - done
            //status 10 - planned, 11 - done, 12 - not done

            if (!item.getAchievementMonday().getDate().isAfter(LocalDate.now())) {
                int status = item.getAchievementMonday().getStatus();
                if (status == 0) {
                    item.getAchievementMonday().setStatus(1);
                } else if (status == 1)  {
                    item.getAchievementMonday().setStatus(0);
                } else if (status == 10) {
                    item.getAchievementMonday().setStatus(11);
                } else if (status == 11) {
                    item.getAchievementMonday().setStatus(12);
                } else {
                    item.getAchievementMonday().setStatus(10);
                }
                achievementService.updateAchievement(item.getAchievementMonday().getId(),
                        item.getHabitId(),
                        new Achievement(item.getAchievementMonday()), userEmail);
                updateList(today);
            } else {
                isIconClicked = true;
                showNotification();
            }
        } else if (dayOfWeek==2){
            if(!item.getAchievementTuesday().getDate().isAfter(LocalDate.now())) {
                int status = item.getAchievementTuesday().getStatus();
                if (status == 0) {
                    item.getAchievementTuesday().setStatus(1);
                } else if (status == 1)  {
                    item.getAchievementTuesday().setStatus(0);
                } else if (status == 10) {
                    item.getAchievementTuesday().setStatus(11);
                } else if (status == 11) {
                    item.getAchievementTuesday().setStatus(12);
                } else {
                    item.getAchievementTuesday().setStatus(10);
                }
                achievementService.updateAchievement(item.getAchievementTuesday().getId(),
                        item.getHabitId(),
                        new Achievement(item.getAchievementTuesday()), userEmail);
                updateList(today);
            } else {
                isIconClicked = true;
                showNotification();
            }
        } else if (dayOfWeek==3) {
            if(!item.getAchievementWednesday().getDate().isAfter(LocalDate.now())) {
                int status = item.getAchievementWednesday().getStatus();
                if (status == 0) {
                    item.getAchievementWednesday().setStatus(1);
                } else if (status == 1)  {
                    item.getAchievementWednesday().setStatus(0);
                } else if (status == 10) {
                    item.getAchievementWednesday().setStatus(11);
                } else if (status == 11) {
                    item.getAchievementWednesday().setStatus(12);
                } else {
                    item.getAchievementWednesday().setStatus(10);
                }
                achievementService.updateAchievement(item.getAchievementWednesday().getId(),
                        item.getHabitId(),
                        new Achievement(item.getAchievementWednesday()), userEmail);
                updateList(today);
            } else {
                isIconClicked = true;
                showNotification();
            }
        } else if(dayOfWeek==4) {
            if(!item.getAchievementThursday().getDate().isAfter(LocalDate.now())) {
                int status = item.getAchievementThursday().getStatus();
                if (status == 0) {
                    item.getAchievementThursday().setStatus(1);
                } else if (status == 1)  {
                    item.getAchievementThursday().setStatus(0);
                } else if (status == 10) {
                    item.getAchievementThursday().setStatus(11);
                } else if (status == 11) {
                    item.getAchievementThursday().setStatus(12);
                } else {
                    item.getAchievementThursday().setStatus(10);
                }
                achievementService.updateAchievement(item.getAchievementThursday().getId(),
                        item.getHabitId(),
                        new Achievement(item.getAchievementThursday()), userEmail);
                updateList(today);
            } else {
                isIconClicked = true;
                showNotification();
            }
        } else if(dayOfWeek==5) {
            if(!item.getAchievementFriday().getDate().isAfter(LocalDate.now())) {
                int status = item.getAchievementFriday().getStatus();
                if (status == 0) {
                    item.getAchievementFriday().setStatus(1);
                } else if (status == 1)  {
                    item.getAchievementFriday().setStatus(0);
                } else if (status == 10) {
                    item.getAchievementFriday().setStatus(11);
                } else if (status == 11) {
                    item.getAchievementFriday().setStatus(12);
                } else {
                    item.getAchievementFriday().setStatus(10);
                }
                achievementService.updateAchievement(item.getAchievementFriday().getId(),
                        item.getHabitId(),
                        new Achievement(item.getAchievementFriday()), userEmail);
                updateList(today);
            } else {
                isIconClicked = true;
                showNotification();
            }
        } else if (dayOfWeek==6) {
            if(!item.getAchievementSaturday().getDate().isAfter(LocalDate.now())) {
                int status = item.getAchievementSaturday().getStatus();
                if (status == 0) {
                    item.getAchievementSaturday().setStatus(1);
                } else if (status == 1)  {
                    item.getAchievementSaturday().setStatus(0);
                } else if (status == 10) {
                    item.getAchievementSaturday().setStatus(11);
                } else if (status == 11) {
                    item.getAchievementSaturday().setStatus(12);
                } else {
                    item.getAchievementSaturday().setStatus(10);
                }
                achievementService.updateAchievement(item.getAchievementSaturday().getId(),
                        item.getHabitId(),
                        new Achievement(item.getAchievementSaturday()), userEmail);
                updateList(today);
            } else {
                isIconClicked = true;
                showNotification();
            }
        } else if (dayOfWeek==7) {
            if(!item.getAchievementSunday().getDate().isAfter(LocalDate.now())) {
                int status = item.getAchievementSunday().getStatus();
                if (status == 0) {
                    item.getAchievementSunday().setStatus(1);
                } else if (status == 1)  {
                    item.getAchievementSunday().setStatus(0);
                } else if (status == 10) {
                    item.getAchievementSunday().setStatus(11);
                } else if (status == 11) {
                    item.getAchievementSunday().setStatus(12);
                } else {
                    item.getAchievementSunday().setStatus(10);
                }
                achievementService.updateAchievement(item.getAchievementSunday().getId(),
                        item.getHabitId(),
                        new Achievement(item.getAchievementSunday()), userEmail);
                updateList(today);
            } else {
                isIconClicked = true;
                showNotification();
            }
        }
    }

    private void showNotification() {
        Div message = new Div();
        message.setSizeFull();
        message.setText("You can't update the future!");
        message.getStyle().set("color", "red").set("font-size", "24px");
        Notification show = new Notification(message);
        show.setPosition(Notification.Position.MIDDLE);
        show.setDuration(3000);
        show.open();
    }

    public HabitService getHabitService() {
        return habitService;
    }

    public void updateList(LocalDate today) {
        Collection<List<Achievement>> achievementsForHabit = findAchievementsForHabit(getAchievementsForWeek(today));
        List<HabitForGrid> listForGrid = new ArrayList<>();
        for(List<Achievement> tempList : achievementsForHabit) {
            Habit habit = tempList.get(0).getHabit();
            listForGrid.add(new HabitForGrid(habit, tempList));
            }
        grid.setItems(listForGrid);
        }

    private List<Achievement> getAchievementsForWeek(LocalDate today) {
        List<LocalDate> week = DateUtils.createWeek(today);
        return achievementService
                .getAllBetweenDates(week.get(0), week.get(week.size() - 1), userEmail);
    }

    private Collection<List<Achievement>> findAchievementsForHabit(List<Achievement> achievementsForWeek) {
        Map<Integer, List<Achievement>> collect = achievementsForWeek.stream()
                .collect(Collectors.groupingBy(list -> list.getHabit().getId()));
        return collect.values();
    }

    public LocalDate getToday() {
        return today;
    }

    public Grid<HabitForGrid> getGrid() {
        return grid;
    }

    public Button getAddHabit() {
        return addHabit;
    }

    public String getUserEmail() {
        return userEmail;
    }
}

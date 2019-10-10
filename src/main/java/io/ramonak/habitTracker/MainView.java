package io.ramonak.habitTracker;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PWA(name = "Habit Tracker Application", shortName = "Habit Tracker")
@Viewport("width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes")
public class MainView extends VerticalLayout implements RouterLayout {

    public MainView() {
    }

}

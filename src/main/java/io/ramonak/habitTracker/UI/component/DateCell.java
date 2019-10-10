package io.ramonak.habitTracker.UI.component;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.ramonak.habitTracker.UI.DTO.DateDTO;

import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Tag("date-cell")
public class DateCell extends VerticalLayout {

    public DateCell(DateDTO dateDTO) {
        String dow = dateDTO.getDow().getDisplayName(TextStyle.SHORT, Locale.UK);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM");
        String date = formatter.format(dateDTO.getDay());

        add(new Div(new Label(dow)), new Div(new Label(date)));
    }
}

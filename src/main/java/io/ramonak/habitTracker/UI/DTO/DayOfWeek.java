package io.ramonak.habitTracker.UI.DTO;

public enum DayOfWeek {

    MONDAY (1),
    TUESDAY (2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY (5),
    SATURDAY (6),
    SUNDAY (7);

    private Integer dow;

    DayOfWeek(Integer dow) {
        this.dow = dow;
    }

    public Integer getDow() {
        return dow;
    }
}

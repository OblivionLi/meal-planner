package mealplanner;

import java.util.ArrayList;
import java.util.List;

public enum Day {
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),
    SATURDAY("Saturday"),
    SUNDAY("Sunday");

    private final String day;

    Day(String day) {
        this.day = day;
    }

    public String getDay() {
        return this.day;
    }

    public static List<String> getAllDays() {
        List<String> days = new ArrayList<>();
        for (Day day : Day.values()) {
            days.add(day.getDay());
        }
        return days;
    }
}

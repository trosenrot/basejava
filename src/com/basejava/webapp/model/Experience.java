package com.basejava.webapp.model;

import java.time.YearMonth;
import java.util.Objects;

public class Experience {

    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String title;
    private final String description;

    public Experience(YearMonth startDate, YearMonth endDate, String title, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(title, that.title) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        if (startDate != null && endDate != null) {
            return "" +
                    startDate + "-" + endDate + "\n" +
                    title + "\n" +
                    description;
        } else {
            return "" +
                    startDate + "-" + " Сейчас\n" +
                    title + "\n" +
                    description;
        }
    }
}

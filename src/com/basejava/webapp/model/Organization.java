package com.basejava.webapp.model;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Experience> content = new ArrayList<>();
    private final Link homePage;

    public Organization(String name, String url) {
        this.homePage = new Link (name, url);
    }

    public String getName() {
        return homePage.getName();
    }

    public void addContent(Experience experience) {
        content.add(experience);
    }

    public List<Experience> getContent() {
        return new ArrayList<>(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, content);
    }

    @Override
    public String toString() {
        return "" + homePage + '\n' +
                outputContent();
    }

    private String outputContent() {
        String text = "";
        for (Experience cont : content) {
            text = text + cont.toString() + "\n";
        }
        return text;
    }

    public static class Experience implements Serializable {
        private static final long serialVersionUID = 1L;

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
}

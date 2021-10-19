package com.basejava.webapp.model;

import com.basejava.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    public Organization() {
    }

    private static final long serialVersionUID = 1L;

    private final List<Experience> content = new ArrayList<>();
    private Link homePage;

    public Organization(String name, String url) {
        this.homePage = new Link(name, url);
    }

    public String getName() {
        return homePage.getName();
    }

    public Link getFullName() {
        return homePage;
    }

    public void addContent(Experience experience) {
        content.add(experience);
    }

    public List<Experience> getContent() {
        return new ArrayList<>(content);
    }

    public int size() {
        return content.size();
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Experience implements Serializable {
        public Experience() {
        }

        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private YearMonth startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private YearMonth endDate;
        private String title;
        private String description;

        public Experience(YearMonth startDate, YearMonth endDate, String title, String description) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public List<String> getContent() {
            List<String> array = new ArrayList<>();
            array.add(startDate.toString());
            array.add(endDate.toString());
            array.add(title);
            array.add(description);
            return array;
        }

        public YearMonth getStartDate() {
            return startDate;
        }

        public YearMonth getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
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

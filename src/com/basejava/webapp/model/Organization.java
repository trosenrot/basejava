package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private final String name;
    private final List<Experience> content = new ArrayList<>();

    public Organization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
        return Objects.equals(name, that.name) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, content);
    }

    @Override
    public String toString() {
        return "" + name + '\n' +
                outputContent();
    }

    private String outputContent() {
        String text = "";
        for (Experience cont : content) {
            text = text + cont.toString() + "\n";
        }
        return text;
    }
}

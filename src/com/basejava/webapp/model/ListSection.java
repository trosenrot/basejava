package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection<String> {

    private final List<String> content = new ArrayList<>();

    @Override
    public void setContent(String content) {
        this.content.add(content);
    }

    @Override
    public String toString() {
        String text = "";
        for (String cnt : content) {
            text = text + "- " + cnt + "\n";
        }
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListSection list = (ListSection) o;

        return content.equals(list.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}

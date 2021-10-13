package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class TextSection extends AbstractSection<String> {
    private static final long serialVersionUID = 1L;

    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public List<String> getContents() {
        List<String> array = new ArrayList<>();
        array.add(content);
        return array;
    }

    public TextSection() {
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public String toString() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextSection text = (TextSection) o;

        return content.equals(text.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}

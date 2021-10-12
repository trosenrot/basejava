package com.basejava.webapp.model;

public class TextSection extends AbstractSection<String> {
    private static final long serialVersionUID = 1L;

    private String content;

    public TextSection(String content) {
        this.content = content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TextSection() {
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

package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Organization> content = new ArrayList<>();

    public OrganizationSection() {
    }

    public OrganizationSection(Organization content) {
        this.content.add(content);
    }

    public OrganizationSection(List<Organization> content) {
        this.content = content;
    }

    public void setContent(Organization content) {
        this.content.add(content);
    }

    public List<Organization> getContent() {
        return new ArrayList<>(content);
    }

    public int size() {
        return content.size();
    }

    @Override
    public String toString() {
        String text = "";
        for (Organization org : content) {
            text = text + org.toString();
        }
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection array = (OrganizationSection) o;

        return content.equals(array.content);
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}

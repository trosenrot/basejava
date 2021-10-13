package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection<Organization> {
    private static final long serialVersionUID = 1L;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization content) {
        this.content.add(content);
    }

    private List<Organization> content = new ArrayList<>();

    @Override
    public void setContent(Organization content) {
        this.content.add(content);
    }

    @Override
    public List<String> getContents() {
        List<String> array = new ArrayList<>();
        for (Organization org : content) {
            array.addAll(org.getContents());
        }
        return array;
    }

    public List<Organization> getContent() {
        return new ArrayList<>(content);
    }

    @Override
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

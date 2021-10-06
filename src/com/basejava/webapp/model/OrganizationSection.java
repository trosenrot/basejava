package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection<Organization> {
    private static final long serialVersionUID = 1L;

    private List<Organization> content = new ArrayList<>();

    public void setContent(Organization content) {
        this.content.add(content);
    }

    public List<Organization> getContent() {
        return new ArrayList<>(content);
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

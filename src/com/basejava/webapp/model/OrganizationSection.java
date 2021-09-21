package com.basejava.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection{
    private List<Organization> content = new ArrayList<>();

    public void setOrganization(Organization content) {
        this.content.add(content);
    }

    @Override
    public String toString() {
        String text = "";
        for (Organization org : content) {
            text = text + org.toString()+ "\n";
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

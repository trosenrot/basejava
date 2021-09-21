package com.basejava.webapp.model;

import java.time.YearMonth;

public class Organization {

    private final String name;
    private final YearMonth startDate;
    private final YearMonth endDate;
    private final String title;
    private final String description;

    public Organization(String name, YearMonth startDate, YearMonth endDate, String title, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Organization organization = (Organization) o;

        if (!name.equals(organization.name)) return false;
        if (!startDate.equals(organization.startDate)) return false;
        if (!endDate.equals(organization.endDate)) return false;
        if (!title.equals(organization.title)) return false;
        return description.equals(organization.description);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        if (startDate!=null && endDate!=null) {
            return "" + name + '\n' +
                    startDate + "-" + endDate + "\n" +
                    title + "\n" +
                    description;
        }
        else {
            return "" + name + '\n' +
                    startDate + "-" + " Сейчас\n" +
                    title + "\n" +
                    description;
        }
    }
}

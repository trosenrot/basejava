package com.basejava.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    HOME_PHONE("Домашний телефон"),
    E_MAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль StackOverFlow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

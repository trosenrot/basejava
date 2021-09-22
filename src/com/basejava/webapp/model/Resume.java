package com.basejava.webapp.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public void setContact(ContactType type, String content) {
        contacts.put(type, content);
    }

    public void setSections(SectionType type, Section content) {
        sections.put(type, content);
    }

    @Override
    public String toString() {
        return "uuid: " + uuid + "\n" +
                "Имя: " + fullName + "\n" +
                (!contacts.isEmpty() ? outputContacts() : "") +
                (!sections.isEmpty() ? outputSections() : "")
                ;
    }

    private String outputContacts() {
        String text = "";
        for (ContactType contactType : ContactType.values()) {
            if (contacts.containsKey(contactType)) {
                text = text + contactType.getTitle() + ": " + contacts.get(contactType) + "\n";
            }
        }
        return text;
    }

    private String outputSections() {
        String text = "";
        for (SectionType sectionType : SectionType.values()) {
            if (sections.containsKey(sectionType)) {
                text = text + sectionType.getTitle() + ": \n" + sections.get(sectionType) + "\n";
            }
        }
        return text;
    }

    @Override
    public int compareTo(Resume o) {
        int compare = fullName.compareTo(o.fullName);
        return compare == 0 ? uuid.compareTo(o.uuid) : compare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName) && Objects.equals(contacts, resume.contacts) && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }
}
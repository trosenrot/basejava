package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serialization {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeWithException(contacts.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });
            Map<SectionType, AbstractSection> sections = r.getSections();
            writeWithException(sections.entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                writeValue(dos, entry.getKey(), entry.getValue());
            });
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            getValue(dis, () -> resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            getValue(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.setSections(sectionType, readAbstractSection(dis, sectionType));
            });
            return resume;
        }
    }

    private void writeValue(DataOutputStream dos, SectionType sectionType, AbstractSection value) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                dos.writeUTF(((TextSection) value).getContent());
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                writeWithException(((ListSection) value).getContent(), dos, dos::writeUTF);
                break;
            case EDUCATION:
            case EXPERIENCE:
                writeWithException(((OrganizationSection) value).getContent(), dos, orgs -> {
                    Link name = orgs.getFullName();
                    dos.writeUTF(name.getName());
                    dos.writeUTF(name.getUrl() != null ? name.getUrl() : "");
                    writeWithException(orgs.getContent(), dos, experience -> {
                        writeDate(dos, experience.getStartDate());
                        writeDate(dos, experience.getEndDate());
                        dos.writeUTF(experience.getTitle());
                        dos.writeUTF(experience.getDescription() == null ? "" : experience.getDescription());
                    });
                });
                break;
            default:
                throw new StorageException("Error section type");
        }

    }

    private void writeDate(DataOutputStream dos, YearMonth date) throws IOException {
        dos.writeUTF(String.valueOf(date));
    }

    private AbstractSection readAbstractSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                int sizeList = dis.readInt();
                List<String> array = new ArrayList<>();
                for (int j = 0; j < sizeList; j++) {
                    array.add(dis.readUTF());
                }
                return new ListSection(array);
            case EDUCATION:
            case EXPERIENCE:
                int sizeOrgs = dis.readInt();
                String name = dis.readUTF();
                String url = dis.readUTF();
                if (url.equals("")) {
                    url = null;
                }
                Organization organization = new Organization(name, url);
                int sizeOrganization = dis.readInt();
                for (int k = 0; k < sizeOrganization; k++) {
                    YearMonth startDate = YearMonth.parse(dis.readUTF());
                    YearMonth endDate = YearMonth.parse(dis.readUTF());
                    String title = dis.readUTF();
                    String description = dis.readUTF();
                    if (description.equals("")) {
                        description = null;
                    }
                    organization.addContent(new Organization.Experience(startDate, endDate, title, description));
                }
                return new OrganizationSection(organization);
            default:
                throw new StorageException("Error section type");
        }
    }

    private void getValue(DataInputStream dis, ReaderValue reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.readValue();
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, WriterValue<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T content : collection) {
            writer.writeValue(content);
        }
    }

    private interface WriterValue<T> {
        void writeValue(T content) throws IOException;
    }

    private interface ReaderValue {
        void readValue() throws IOException;
    }
}

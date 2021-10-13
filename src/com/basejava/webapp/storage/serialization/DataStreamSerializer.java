package com.basejava.webapp.storage.serialization;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements Serialization {
    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = r.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                List<String> arraySection = entry.getValue().getContents();
                dos.writeInt(arraySection.size());
                for (String array : arraySection) {
                    dos.writeUTF(array);
                }
            }
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

    private AbstractSection readAbstractSection(DataInputStream dis, SectionType sectionType) throws IOException {
        int sizeSection = dis.readInt();
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> array = new ArrayList<>();
                for (int j = 0; j < sizeSection; j++) {
                    array.add(dis.readUTF());
                }
                return new ListSection(array);
            case EDUCATION:
            case EXPERIENCE:
                Organization organization = new Organization(dis.readUTF(), dis.readUTF());
                for (int k = 0; k < (sizeSection - 2) / 4; k++) {
                    organization.addContent(new Organization.Experience(YearMonth.parse(dis.readUTF()), YearMonth.parse(dis.readUTF()), dis.readUTF(), dis.readUTF()));
                }
                return new OrganizationSection(organization);
            default:
                throw new StorageException("Error section type", null);
        }
    }

    private void getValue(DataInputStream dis, ReaderValue reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.readValue();
        }
    }

    private interface ReaderValue {
        void readValue() throws IOException;
    }
}

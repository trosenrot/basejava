package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[list.size()]);
    }

    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected final void saveResume(Resume resume, int index) {
        list.add(resume);
    }

    @Override
    protected final void deleteResume(int index, String uuid) {
        list.remove(index);
    }

    @Override
    protected final void updateResume(Resume resume, int index) {
        list.set(index, resume);
    }

    @Override
    public final int size() {
        return list.size();
    }

    @Override
    public final void clear() {
        list.clear();
    }

    @Override
    public final Resume getResume(int index, String uuid) {
        return list.get(index);
    }
}

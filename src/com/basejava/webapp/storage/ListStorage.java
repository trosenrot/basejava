package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(list);
    }

    @Override
    protected Object findKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected final void saveResume(Resume resume, Object key) {
        list.add(resume);
    }

    @Override
    protected final void deleteResume(Object key) {
        list.remove((Integer) key);
    }

    @Override
    protected final void updateResume(Resume resume, Object key) {
        list.set((Integer) key, resume);
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
    public final Resume getResume(Object key) {
        return list.get((Integer) key);
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key > 0;
    }
}

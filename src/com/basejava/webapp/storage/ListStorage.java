package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> list = new ArrayList<>();

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(list);
    }

    @Override
    protected Integer findKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected final void saveResume(Resume resume, Integer key) {
        list.add(resume);
    }

    @Override
    protected final void deleteResume(Integer key) {
        list.remove(key);
    }

    @Override
    protected final void updateResume(Resume resume, Integer key) {
        list.set(key, resume);
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
    public final Resume getResume(Integer key) {
        return list.get(key);
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }
}

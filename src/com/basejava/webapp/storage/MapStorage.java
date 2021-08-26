package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    HashMap<String, Resume> map = new HashMap<>();

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    protected int findIndex(String uuid) {
        if (!map.containsKey(uuid)) {
            return -1;
        }
        return 1;
    }

    @Override
    protected void deleteResume(int index, String uuid) {
        map.remove(uuid);
    }

    @Override
    protected void saveResume(Resume resume, int index) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateResume(Resume resume, int index) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return map.get(uuid);
    }
}

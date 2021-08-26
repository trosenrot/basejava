package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    HashMap<String, Resume> map = new HashMap<>();


    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    protected int findIndex(String uuid) {
        if (!map.containsKey(uuid)) {
            return -1;
        }
        return 1;
    }

    protected void deleteResume(int index, String uuid) {
        map.remove(uuid);
    }

    protected void saveResume(Resume resume, int index) {
        map.put(resume.getUuid(), resume);
    }

    protected void updateResume(Resume resume, int index) {
        map.put(resume.getUuid(), resume);
    }

    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    protected Resume getResume(int index, String uuid) {
        return map.get(uuid);
    }
}

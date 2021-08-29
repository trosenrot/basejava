package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    protected Object findKey(String uuid) {
        return map.containsKey(uuid) ? uuid : null;
    }

    @Override
    protected void deleteResume(Object key) {
        map.remove((String) key);
    }

    @Override
    protected void saveResume(Resume resume, Object key) {
        map.put((String) key, resume);
    }

    @Override
    protected void updateResume(Resume resume, Object key) {
        map.put((String) key, resume);
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
    protected Resume getResume(Object key) {
        return map.get((String) key);
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }
}

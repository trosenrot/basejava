package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected Object findKey(String uuid) {
        Resume[] values = map.values().toArray(new Resume[0]);
        for (Resume r: values) {
            if (uuid.equals(r.getUuid())) {
                return uuid;
            }
        }
        return null;
    }

    @Override
    protected void deleteResume(Object key) {
        map.remove((String) key);
    }

    @Override
    protected void saveResume(Resume resume, Object key) {
        key = resume.getUuid();
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

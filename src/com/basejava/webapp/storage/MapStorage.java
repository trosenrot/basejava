package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected String findKey(String uuid) {
        return map.containsKey(uuid) ? uuid : null ;
    }

    @Override
    protected void deleteResume(String key) {
        map.remove(key);
    }

    @Override
    protected void saveResume(Resume resume, String key) {
        key = resume.getUuid();
        map.put(key, resume);
    }

    @Override
    protected void updateResume(Resume resume, String key) {
        map.put(key, resume);
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
    protected Resume getResume(String key) {
        return map.get(key);
    }

    @Override
    protected boolean isExist(String key) {
        return key != null;
    }
}

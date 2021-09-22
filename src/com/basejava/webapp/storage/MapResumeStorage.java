package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected Resume findKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void deleteResume(Resume key) {
        map.remove(key.getUuid());
    }

    @Override
    protected void saveResume(Resume resume, Resume key) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateResume(Resume resume, Resume key) {
        map.put(key.getUuid(), resume);
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
    protected Resume getResume(Resume key) {
        return key;
    }

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }
}

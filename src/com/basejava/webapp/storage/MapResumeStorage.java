package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    protected List<Resume> getAsList() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected Object findKey(String uuid) {return map.get(uuid);}

    @Override
    protected void deleteResume(Object key) {
        Resume r = (Resume) key;
        map.remove(r.getUuid());
    }

    @Override
    protected void saveResume(Resume resume, Object key) {
        key = resume.getUuid();
        map.put((String) key, resume);
    }

    @Override
    protected void updateResume(Resume resume, Object key) {
        Resume r = (Resume) key;
        map.put(r.getUuid(), resume);
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
        return (Resume) key;
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }
}

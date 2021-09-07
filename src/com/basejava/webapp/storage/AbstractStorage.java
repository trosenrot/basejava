package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    public final void save(Resume resume) {
        Object key = getKeyIfResumeNotExist(resume.getUuid());
        saveResume(resume, key);
    }

    public final void delete(String uuid) {
        Object key = getKeyIfResumeExist(uuid);
        deleteResume(key);
    }

    public final void update(Resume resume) {
        Object key = getKeyIfResumeExist(resume.getUuid());
        updateResume(resume, key);
    }

    public final Resume get(String uuid) {
        Object key = getKeyIfResumeExist(uuid);
        return getResume(key);
    }

    private Object getKeyIfResumeExist(String uuid) {
        Object key = findKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private Object getKeyIfResumeNotExist(String uuid) {
        Object key = findKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    protected abstract boolean isExist(Object key);

    protected abstract Object findKey(String uuid);

    protected abstract void deleteResume(Object key);

    protected abstract void saveResume(Resume resume, Object key);

    protected abstract void updateResume(Resume resume, Object key);

    protected abstract Resume getResume(Object key);

    protected abstract List<Resume> getAll();
}

package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        int index = getKeyIfResumeExist(resume.getUuid());
        saveResume(resume, index);
    }

    public void delete(String uuid) {
        int index = getKeyIfResumeNotExist(uuid);
        deleteResume(index, uuid);
    }

    public void update(Resume resume) {
        int index = getKeyIfResumeNotExist(resume.getUuid());
        updateResume(resume, index);
    }

    public Resume get(String uuid) {
        int index = getKeyIfResumeNotExist(uuid);
        return getResume(index, uuid);
    }

    private int getKeyIfResumeNotExist(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    private int getKeyIfResumeExist(String uuid) {
        int index = findIndex(uuid);
        if (index > -1) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    public abstract Resume[] getAll();

    protected abstract int findIndex(String uuid);

    protected abstract void deleteResume(int index, String uuid);

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void updateResume(Resume resume, int index);

    public abstract int size();

    public abstract void clear();

    protected abstract Resume getResume(int index, String uuid);
}

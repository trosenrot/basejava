package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        ExistStorageException(index, resume.getUuid());
        saving(resume, index);
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        notExistStorage(index, uuid);
        deletion(index);
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        notExistStorage(index, resume.getUuid());
        updating(resume, index);
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        notExistStorage(index, uuid);
        return getting(index);
    }

    protected void notExistStorage(int index, String uuid) {
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected void ExistStorageException(int index, String uuid) {
        if (index > -1) {
            throw new ExistStorageException(uuid);
        }
    }

    public abstract Resume[] getAll();

    protected abstract int findIndex(String uuid);

    protected abstract void deletion(int index);

    protected abstract void saving(Resume resume, int index);

    protected abstract void updating(Resume resume, int index);

    public abstract int size();

    public abstract void clear();

    protected abstract Resume getting(int index);
}

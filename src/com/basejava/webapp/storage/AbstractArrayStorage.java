package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final void saveResume(Resume resume, Object index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("БД переполнена!", resume.getUuid());
        }
        saveToArray(resume, index);
        size++;
    }

    @Override
    protected void deleteResume(Object key) {
        int index = (Integer) key;
        if (index < size - 1) {
            System.arraycopy(storage, index + 1, storage, index, (size - 1) - index);
        }
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateResume(Resume resume, Object key) {
        storage[(Integer) key] = resume;
    }

    @Override
    protected Resume getResume(Object key) {
        return storage[(Integer) key];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object key) {
        return (Integer) key > 0;
    }

    protected abstract Object findKey(String uuid);

    protected abstract void saveToArray(Resume resume, Object index);
}
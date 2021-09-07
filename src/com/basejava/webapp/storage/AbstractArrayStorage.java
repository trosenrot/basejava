package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.*;

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
    protected final void saveResume(Resume resume, Object key) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("БД переполнена!", resume.getUuid());
        }
        saveToArray(resume, size);
        size++;
    }

    @Override
    protected void deleteResume(Object key) {
        int index = (int) key;
        if (index < size - 1) {
            System.arraycopy(storage, index + 1, storage, index, (size - 1) - index);
        }
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateResume(Resume resume, Object key) {
        storage[(int) key] = resume;
    }

    @Override
    protected Resume getResume(Object key) {
        return storage[(int) key];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object key) {
        return (int) key >= 0;
    }

    protected abstract Object findKey(String uuid);

    protected abstract void saveToArray(Resume resume, int index);
}
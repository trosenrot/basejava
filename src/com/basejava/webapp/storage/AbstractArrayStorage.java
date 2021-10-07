package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage <Integer> {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final void saveResume(Resume resume, Integer key) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("БД переполнена!", resume.getUuid());
        }
        saveToArray(resume, size);
        size++;
    }

    @Override
    protected void deleteResume(Integer key) {
        if (key < size - 1) {
            System.arraycopy(storage, key + 1, storage, key, (size - 1) - key);
        }
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected void updateResume(Resume resume, Integer key) {
        storage[key] = resume;
    }

    @Override
    protected Resume getResume(Integer key) {
        return storage[key];
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
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    protected abstract Integer findKey(String uuid);

    protected abstract void saveToArray(Resume resume, int index);
}
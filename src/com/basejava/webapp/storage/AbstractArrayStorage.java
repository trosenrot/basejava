package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected final void saving(Resume resume, int index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("БД переполнена!", resume.getUuid());
        }
        saveToArray(resume, index);
        size++;
    }

    @Override
    protected final void deletion(int index) {
        if (index < size - 1) {
            System.arraycopy(storage, index + 1, storage, index, (size - 1) - index);
        }
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected final void updating(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected final Resume getting(int index) {
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public final int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveToArray(Resume resume, int index);
}
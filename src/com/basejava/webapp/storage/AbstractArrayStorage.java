package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("ERROR: резюме " + resume.getUuid() + " не существует!");
            return;
        }
        storage[index] = resume;
    }

    public final void save(Resume r) {
        if (findIndex(r.getUuid()) > -1) {
            System.out.println("ERROR: резюме " + r.getUuid() + " уже существует!");
            return;
        }
        if (size >= STORAGE_LIMIT) {
            System.out.println("ERROR: Добавление резюме не возможно, БД переполнена!");
            return;
        }
        saveToArray(r);
        size++;
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: резюме " + uuid + " не существует!");
            return null;
        }
        return storage[index];
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("ERROR: резюме " + uuid + " не существует!");
            return;
        }
        if (index < size - 1) {
            System.arraycopy(storage, index + 1, storage, index, (size - 1) - index);
        }
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final int size() {
        return size;
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveToArray(Resume resume);
}
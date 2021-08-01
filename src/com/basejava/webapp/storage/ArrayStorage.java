package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

import java.util.Scanner;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (checkResume(r.getUuid()) != -1) {
            System.out.println("ERROR: резюме уже существует!");
            return;
        }
        if (size == 10000) {
            System.out.println("ERROR: Добавление резюме не возможно, БД переполнена!");
            return;
        }
        storage[size] = r;
        size++;
    }

    public void update(Resume resume) {
        int i;
        if ((i = checkResume(resume.getUuid())) == -1) {
            System.out.println("ERROR: резюме не существует!");
            return;
        }
        Scanner console = new Scanner(System.in);
        System.out.print("Введите новый uuid: ");
        storage[i].setUuid(console.nextLine());
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        int i;
        if ((i = checkResume(uuid)) == -1) {
            System.out.println("ERROR: резюме не существует!");
            return;
        }
        storage[i] = null;
        for (; i < size - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size - 1] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public int checkResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}


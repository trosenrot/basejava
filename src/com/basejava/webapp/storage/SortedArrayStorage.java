package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object findKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveToArray(Resume resume, int key) {
        key = -(key) - 1;
        System.arraycopy(storage, key, storage, key + 1, size - key);
        storage[key] = resume;
    }
}
package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    protected void saveToArray(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            index = -(index) - 1;
        }
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }
}
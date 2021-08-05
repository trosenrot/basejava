package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void sort() {
        for (int i = 1; i < size; i++) {
            Resume element = storage[i];
            int index = Arrays.binarySearch(storage, 0, i, element);
            if (index < 0) {
                index = -(index) - 1;
            }
            System.arraycopy(storage, index, storage, index + 1, i - index);
            storage[index] = element;
        }
    }
}

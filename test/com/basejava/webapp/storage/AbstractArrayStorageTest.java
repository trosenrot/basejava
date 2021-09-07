package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static com.basejava.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 3; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("fullName"));
            }
        } catch (StorageException e) {
            Assert.fail("Переполнение произошло раньше времени!");
        }
        storage.save(new Resume("fullName"));
    }
}
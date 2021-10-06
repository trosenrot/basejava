package com.basejava.webapp.storage;

import com.basejava.webapp.serialization.ObjectInput_OutputStream;

import static org.junit.Assert.*;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR, new ObjectInput_OutputStream()));
    }
}
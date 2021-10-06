package com.basejava.webapp.storage;

import com.basejava.webapp.serialization.ObjectInput_OutputStream;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR, new ObjectInput_OutputStream()));
    }
}
package com.basejava.webapp.storage;

import com.basejava.webapp.serialization.Serialization;

import java.io.File;

public class ObjectStreamPathStorage extends AbstractPathStorage {

    protected ObjectStreamPathStorage(File directory, Serialization serialization) {
        super(directory.toString(), serialization);
    }
}

package com.basejava.webapp.storage;

import com.basejava.webapp.serialization.Serialization;

import java.io.File;

public class ObjectStreamStorage extends AbstractFileStorage {

    protected ObjectStreamStorage(File directory, Serialization serialization) {
        super(directory, serialization);
    }
}

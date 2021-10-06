package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.serialization.Serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    private Serialization serialization;

    protected AbstractFileStorage(File directory, Serialization serialization) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serialization = serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        if (files == null) {
            return 0;
        }
        return files.length;
    }

    @Override
    protected File findKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateResume(Resume r, File file) {
        try {
            serialization.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected void saveResume(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        updateResume(r, file);
    }


    @Override
    protected Resume getResume(File file) {
        try {
            return serialization.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void deleteResume(File file) {
        file.delete();
    }

    @Override
    protected List<Resume> getAsList() {
        File[] files = directory.listFiles();
        if (files == null) {
            return null;
        }
        List<Resume> list = new ArrayList<>();
        for (File file : files) {
            list.add(getResume(file));
        }
        return list;
    }
}

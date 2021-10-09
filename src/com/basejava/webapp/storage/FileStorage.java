package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serialization.Serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;

    private Serialization serialization;

    protected FileStorage(File directory, Serialization serialization) {
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

    @Override
    public void clear() {
        for (File file : getFiles()) {
            deleteResume(file);
        }
    }

    @Override
    public int size() {
        return getFiles().length;
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
            throw new StorageException("Write error", directory.toString(), e);
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
            throw new StorageException("Read error", directory.toString(), e);
        }
    }


    @Override
    protected void deleteResume(File file) {
        if (!file.delete()) {
            throw new StorageException("Couldn't delete file", directory.toString());
        }
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> list = new ArrayList<>();
        for (File file : getFiles()) {
            list.add(getResume(file));
        }
        return list;
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("List files is empty", directory.toString());
        }
        return files;
    }
}

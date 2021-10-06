package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.serialization.Serialization;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private Serialization serialization;

    protected AbstractPathStorage(String dir, Serialization serialization) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serialization = serialization;
    }

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::deleteResume);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    @Override
    protected Path findKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void updateResume(Resume r, Path Path) {
        try {
            serialization.doWrite(r, new BufferedOutputStream(Files.newOutputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path Path) {
        return Files.exists(Path);
    }

    @Override
    protected void saveResume(Resume r, Path Path) {
        try {
            Files.createFile(Path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + Path, Path.toString(), e);
        }
        updateResume(r, Path);
    }

    @Override
    protected Resume getResume(Path Path) {
        try {
            return serialization.doRead(new BufferedInputStream(Files.newInputStream(Path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", Path.toString(), e);
        }
    }

    @Override
    protected void deleteResume(Path Path) {
        try {
            Files.delete(Path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", Path.toString(), e);
        }
    }

    @Override
    protected List<Resume> getAsList() {

        Stream<Path> stream = null;
        try {
            stream = Files.list(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stream.map(this::getResume).collect(Collectors.toList());
    }
}

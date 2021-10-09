package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.serialization.Serialization;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private Serialization serialization;

    protected PathStorage(String dir, Serialization serialization) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serialization = serialization;
    }

    @Override
    public void clear() {
        getPaths("Path delete error").forEach(this::deleteResume);
    }

    @Override
    public int size() {
        return (int) getPaths("Path cannot be count").count();
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
    protected List<Resume> getAll() {
        return getPaths("Path get error").map(this::getResume).collect(Collectors.toList());
    }

    private Stream<Path> getPaths(String message) {

        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException(message, directory.toString(), e);
        }
    }
}

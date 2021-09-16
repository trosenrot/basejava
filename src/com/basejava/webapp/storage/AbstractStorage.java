package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    //    protected final Logger LOG = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public final void save(Resume resume) {
        LOG.info("Save " + resume);
        SK key = getKeyIfResumeNotExist(resume.getUuid());
        saveResume(resume, key);
    }

    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK key = getKeyIfResumeExist(uuid);
        deleteResume(key);
    }

    public final void update(Resume resume) {
        LOG.info("Update " + resume);
        SK key = getKeyIfResumeExist(resume.getUuid());
        updateResume(resume, key);
    }

    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK key = getKeyIfResumeExist(uuid);
        return getResume(key);
    }

    private SK getKeyIfResumeExist(String uuid) {
        SK key = findKey(uuid);
        if (!isExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    private SK getKeyIfResumeNotExist(String uuid) {
        SK key = findKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = getAsList();
        Collections.sort(list);
        return list;
    }

    protected abstract boolean isExist(SK key);

    protected abstract SK findKey(String uuid);

    protected abstract void deleteResume(SK key);

    protected abstract void saveResume(Resume resume, SK key);

    protected abstract void updateResume(Resume resume, SK key);

    protected abstract Resume getResume(SK key);

    protected abstract List<Resume> getAsList();
}

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = -1;

    void clear() {
        Arrays.fill(storage, null);
        size = -1;
    }

    void save(Resume r) {
        size++;
        storage[size] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i <= size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (size == -1) {return;}
        int i;
        for (i = 0; i <= size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                break;
            }
        }
        for (; i < size; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size] = null;
        size--;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size + 1);
    }

    int size() {
        return size + 1;
    }
}

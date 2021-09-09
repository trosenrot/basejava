package com.basejava.webapp;

import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.MapStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Interactive test for com.basejava.webapp.storage.ArrayStorage implementation
 * (just run, no need to understand)
 */
public class MainArray {
 //   private final static ArrayStorage ARRAY_STORAGE = new ArrayStorage();
//    private final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();
 //   private final static ListStorage ARRAY_STORAGE = new ListStorage();
 //   private final static SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();
    private final static MapStorage ARRAY_STORAGE = new MapStorage();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save resume | delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String param_1 = null;
            String param_2 = null;
            if (params.length == 2) {
                param_1 = params[1].intern();
            }
            if (params.length == 3) {
                param_1 = params[1].intern();
                param_2 = params[2].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    if (params.length == 2) {
                        r = new Resume(param_1);
                    } else {
                        r = new Resume(param_1, param_2);
                    }
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "update":
                    r = new Resume(param_1, param_2);
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(param_1);
                    printAll();
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(param_1));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (all.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.println(r.getUuid() + " " + r.getFullName());
            }
        }
        System.out.println("----------------------------");
    }
}
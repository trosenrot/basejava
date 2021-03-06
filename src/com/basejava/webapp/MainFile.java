package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        outputDirectoryStructure(dir, "");
    }

    public static void outputDirectoryStructure(File dir, String indent) {
        File[] arrayFiles = dir.listFiles();
        if (arrayFiles == null) {
            return;
        }
        for (File file : arrayFiles) {
            if (file.isFile()) {
                System.out.println(indent + file.getName());
            } else {
                System.out.println(indent + file.getName() + ":");
                outputDirectoryStructure(file, indent + "  ");
            }
        }
    }
}

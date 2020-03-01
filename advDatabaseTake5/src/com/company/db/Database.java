package com.company.db;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class Database implements DiskOperations, GetFromPath {
    public static final Database instance = new Database();

    private Database() {

    }

    public File[] getFilesFromPath(Path path) {
        return path.toFile().listFiles();
    }

    public void save(Entity entity) {


        Path path = null;
        try {
            path = (Path) entity.getClass().getDeclaredMethod("getPath").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        path = path.resolve(entity.getPathWithId());
        String folder = path.toString().substring(0, path.toString().lastIndexOf("\\"));
        File file = new File(String.valueOf(path));
        try {
            if (file.exists()) {
                System.out.println("Filename " + file.getName() + " already exists in folder " + folder + ", preparing to overwrite file...");
            }
            PrintWriter writer = null;
            writer = new PrintWriter(String.valueOf(path), StandardCharsets.UTF_8);
            writer.println(entity.toString());
            System.out.println("Saved " + file.getName() + " to " + folder + ".");
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> makeListFromTxt(Path path) {

        List<String> contents = null;
        try {
            contents = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }


    public void delete(Entity entity) {
        Path path = null;
        try {
            path = (Path) entity.getClass().getDeclaredMethod("getPath").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        path = path.resolve(entity.getPathWithId());

        if (Files.exists(path)) {
            try {
                Files.delete(path);
                System.out.println("Deleted " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void verifyDirectoryIntegrity(Path path) {
        checkPath(path);
    }

    public void checkPath(Path path) {

        if (!Files.exists(path)) {
            System.out.println(path + " does not exist... creating...");
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Database getInstance() {
        return instance;
    }
}

package com.company.db;

import com.company.lib.Author;
import com.company.lib.Book;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class Database {


    public static File[] getFilesFromPath(Path path) {
        return path.toFile().listFiles();
    }

    public static void save(Entity entity) {

        Path path = null;
        try {
            path = (Path)entity.getClass().getDeclaredMethod("getPath").invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        path = path.resolve(entity.getPathWithId());
        System.out.println("path = " + path);

        File file = new File(String.valueOf(path));
        try {
            if (file.exists()) {
                System.out.println("Filename "+file.toString()+" already exists, overwriting file...");
            }
            PrintWriter writer = null;
            writer = new PrintWriter(String.valueOf(path), StandardCharsets.UTF_8);
            writer.println(entity.toString());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> makeListFromTxt(Path path) {

        List<String> contents = null;
        try {
            contents = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contents;
    }
    public static void delete(Entity entity){
        Path path = null;
        try {
            path = (Path)entity.getClass().getDeclaredMethod("getPath").invoke(null);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        path = path.resolve(entity.getPathWithId());

        if(Files.exists(path)){
            try {
                Files.delete(path);
                System.out.println("Deleted "+path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void verifyDirectoryIntegrity(Path path) {
        checkPath(path);
    }

    private static void checkPath(Path path) {

        if (!Files.exists(path)) {
            System.out.println(path + " does not exist... creating...");
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

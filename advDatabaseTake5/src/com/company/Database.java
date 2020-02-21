package com.company;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class Database {


    public static File[] getFilesFromPath(Path path) {
        return path.toFile().listFiles();
    }

    public static void save(String stringToSave, Path path) {
        File file = new File(String.valueOf(path));
        try {
            if (file.exists()) {
                System.out.println("Filename "+file.toString()+" already exists, overwriting file...");
            }
            PrintWriter writer = null;
            writer = new PrintWriter(String.valueOf(path), StandardCharsets.UTF_8);
            writer.println(stringToSave);
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
    public static void delete(Path path){
        if(Files.exists(path)){
            try {
                Files.delete(path);
                System.out.println("Deleted "+path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

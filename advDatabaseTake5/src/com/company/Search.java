package com.company;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class Search {


    public static ArrayList<Object> findMany(String key, String val, Path path) {
        ArrayList<Object> objectsFound = new ArrayList<>();
        Type type = null;
        Object n = null;
        Method meth = null;
        for (File f : Database.getFilesFromPath(path)) {
            ArrayList<String> a = (ArrayList<String>) Database.makeListFromTxt(f.toPath());
            for (Field field : Book.class.getDeclaredFields()) {
                if (field.getName().equals(key)) {
                    type = Book.class;
                    break;
                }
            }
            if (type == null) {
                for (Field field : Author.class.getDeclaredFields()) {
                    if (field.getName().equals(key)) {
                        type = Author.class;
                        break;
                    }
                }
            }
            try {
                if (type == Book.class) {
                    Book.class.getDeclaredField(key);
                    String makeGetter = "get".concat(Book.class.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + Book.class.getDeclaredField(key).getName().substring(1));
                    meth = Book.class.getDeclaredMethod(makeGetter);
                    n = new Book(a);
                } else if (type == Author.class) {
                    Author.class.getDeclaredField(key);
                    String makeGetter = "get".concat(Author.class.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + Author.class.getDeclaredField(key).getName().substring(1));
                    meth = Author.class.getDeclaredMethod(makeGetter);
                    n = new Author(a);
                }
                //TODO datetime
                if (key.equals("dateTimeCreated") || key.equals("dateTimeAccessed")) {
                    if (LocalDateTime.parse(String.valueOf(meth.invoke(n))).isBefore(LocalDateTime.parse(val))) {
                        objectsFound.add(n);
                    }
                }
                if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).find()) {
                    objectsFound.add(n);
                }
            } catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objectsFound;

    }

    public static Object findOne(String key, String val, Path path) {
        Type type = null;
        Object n = null;
        Method meth = null;
        for (File f : Database.getFilesFromPath(path)) {
            ArrayList<String> a = (ArrayList<String>) Database.makeListFromTxt(f.toPath());
            for (Field field : Book.class.getDeclaredFields()) {
                if (field.getName().equals(key)) {
                    type = Book.class;
                    break;
                }
            }
            if (type == null) {
                for (Field field : Author.class.getDeclaredFields()) {
                    if (field.getName().equals(key)) {
                        type = Author.class;
                        break;
                    }
                }
            }
            try {
                if (type == Book.class) {
                    Book.class.getDeclaredField(key);
                    String makeGetter = "get".concat(Book.class.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + Book.class.getDeclaredField(key).getName().substring(1));
                    meth = Book.class.getDeclaredMethod(makeGetter);
                    n = new Book(a);
                } else if (type == Author.class) {
                    Author.class.getDeclaredField(key);
                    String makeGetter = "get".concat(Author.class.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + Author.class.getDeclaredField(key).getName().substring(1));
                    meth = Author.class.getDeclaredMethod(makeGetter);
                    n = new Author(a);
                }

                if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).find()) {
                    return n;
                }
            } catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Search inconclusive");
        return null;

    }

    static void printResult(ArrayList<Object> arrayList) {
        if (arrayList.size() > 0) {
            for (Object o : arrayList) {
                System.out.println(o.toString() + "\n");
            }
        } else System.out.println("\nSearch came up empty.\n");
    }
}

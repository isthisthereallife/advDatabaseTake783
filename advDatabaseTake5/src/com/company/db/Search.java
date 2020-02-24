package com.company.db;


import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public abstract class Search {

    public static ArrayList<Object> findMany(String key, String val, Class klass) {
        ArrayList<Object> objectsFound = new ArrayList<>();
        Object n = null;
        Method meth = null;
        ArrayList<ArrayList<String>> a = new ArrayList<>();
        try {
            Path path = (Path) klass.getDeclaredMethod("getPath").invoke(null);
            for (File f : Database.getFilesFromPath(path)) {
                a.add((ArrayList<String>) Database.makeListFromTxt(f.toPath()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            String makeGetter = "get".concat(klass.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + klass.getDeclaredField(key).getName().substring(1));
            meth = klass.getDeclaredMethod(makeGetter);

            for (ArrayList<String> b : a) {
                n = klass.getConstructor(List.class).newInstance(b);

                if (key.equals("dateTimeCreated") || key.equals("dateTimeAccessed")) {
                    if (LocalDateTime.parse(String.valueOf(meth.invoke(n))).isBefore(LocalDateTime.parse(val))) {
                        objectsFound.add(n);
                    }
                } else  if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).find()) {
                    objectsFound.add(n);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectsFound;

    }

    public static Optional<Entity> findOne(String key, String val, Class klass) {
        Object n = null;
        Method meth = null;
        Path path = null;
        ArrayList<ArrayList<String>> a = new ArrayList<>();
        try {
            path = (Path) klass.getDeclaredMethod("getPath").invoke(null);

            for (File f : Database.getFilesFromPath(path)) {
                a.add((ArrayList<String>) Database.makeListFromTxt(f.toPath()));
            }

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            String makeGetter = "get".concat(klass.getDeclaredField(key).getName().substring(0,1).toUpperCase() + klass.getDeclaredField(key).getName().substring(1));
            meth = klass.getDeclaredMethod(makeGetter);

            for (ArrayList<String> b : a) {
                n = klass.getConstructor(List.class).newInstance(b);

                if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).find()) {
                    return Optional.of((Entity)n);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }



    public static void printResult(ArrayList<Object> arrayList) {
        if (arrayList.size() > 0) {
            for (Object o : arrayList) {
                System.out.println(o.toString() + "\n");
            }
        } else System.out.println("\nSearch came up empty.\n");
    }
}

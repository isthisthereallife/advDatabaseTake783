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

    public static ArrayList<Entity> findMany(String key, String val, boolean literalSearch, Class klass) {
        ArrayList<Entity> objectsFound = new ArrayList<>();
        Object n = null;
        Method meth = null;
        ArrayList<ArrayList<String>> a = new ArrayList<>();
        try {
            Path path = (Path) klass.getDeclaredMethod("getPath").invoke(null);
            for (File f : Database.getFilesFromPath(path)) {
                a.add((ArrayList<String>) Database.makeListFromTxt(f.toPath()));
            }
        } catch (Exception e) {
            System.out.println("*");
        }

        try {
            String makeGetter = "get".concat(klass.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + klass.getDeclaredField(key).getName().substring(1));
            meth = klass.getDeclaredMethod(makeGetter);

            for (ArrayList<String> b : a) {
                n = klass.getConstructor(List.class).newInstance(b);
                if (key.equals("dateTimeCreated") || key.equals("dateTimeAccessed")) {
                    if (LocalDateTime.parse(String.valueOf(meth.invoke(n))).isBefore(LocalDateTime.parse(val))) {
                        objectsFound.add((Entity) n);
                    }
                } else {
                    if (!literalSearch) {
                        if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).find()) {
                            objectsFound.add((Entity) n);
                        }
                    } else {
                        if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).matches())
                            objectsFound.add((Entity) n);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("**");
        }
        return objectsFound;
    }

    public static Optional<Entity> findOne(String key, String val, boolean literalSearch, Class klass) {
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
            System.out.println("*");
        }

        try {
            String makeGetter = "get".concat(klass.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + klass.getDeclaredField(key).getName().substring(1));
            meth = klass.getDeclaredMethod(makeGetter);

            for (ArrayList<String> b : a) {
                n = klass.getConstructor(List.class).newInstance(b);

                if (key.equals("dateTimeCreated") || key.equals("dateTimeAccessed")) {
                    if (LocalDateTime.parse(String.valueOf(meth.invoke(n))).isBefore(LocalDateTime.parse(val))) {
                        return Optional.of((Entity) n);
                    }
                } else {
                    if (!literalSearch) {
                        if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).find()) {
                            return Optional.of((Entity) n);
                        }
                    } else {
                        if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).matches())
                            return Optional.of((Entity) n);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("*");
        }
        return Optional.empty();
    }

    public static void printResult(ArrayList<Entity> arrayList) {
        if (arrayList.size() > 0) {
            for (Entity o : arrayList) {
                o = (Entity) o;
                System.out.println("\n" + o.toPrettyString() + "\n");
            }
        } else System.out.println("\n*Search came up empty.*\n");
    }

    public static void printResult(Optional<Entity> entity) {
        entity.ifPresentOrElse(i -> System.out.println("\n" + i.toPrettyString() + "\n"), () -> System.out.println("\n*Search came up empty.*\n"));
    }
}

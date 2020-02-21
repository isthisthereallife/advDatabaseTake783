package com.company;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Library {
    Path path = Paths.get("databases/library/");
    Path bookPath = Paths.get(path + "/books");
    Path authorPath = Paths.get(path + "/authors");

    Library() {
        verifyDirectoryIntegrity();

        Book b = new Book("book2", "2222222", "title2", "åthor", "tjanger", "2222");
        save(b, b.path);

        Book d = (Book) findOne("genre", "jan", bookPath);
        if (b.ISBN.equals(d.ISBN)) {
            System.out.println("Same same: " + b.ISBN + " == " + d.ISBN);
            save(d, d.path);
        }


        
        Author a1 = new Author();
        save(a1, a1.path);
        Author a2 = (Author) findOne("firstName", "first", authorPath);
        if (a1.firstName.equals(a2.firstName)) {
            System.out.println(a1.firstName + " == " + a2.firstName);
        }
    }

    public void delete(Path precisePath) {
        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        Database.delete(precisePath);
    }

    public Object findOne(String key, String val, Path path) {
        for (File f : Database.getFilesFromPath(path)) {
            ArrayList<String> a = (ArrayList<String>) Database.makeListFromTxt(f.toPath());
            try {
                Book.class.getDeclaredField(key);
                String makeGetter = "get".concat(Book.class.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + Book.class.getDeclaredField(key).getName().substring(1));
                Method meth = Book.class.getDeclaredMethod(makeGetter);
                Book n = new Book(a);
                if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).find()) {
                    return n;
                }
            } catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                //if we reach this place, we're probably not looking at a book
                break;
            }
        }
        for (File f : Database.getFilesFromPath(path)) {
            ArrayList<String> a = (ArrayList<String>) Database.makeListFromTxt(f.toPath());
            try {
                Author.class.getDeclaredField(key);
                String makeGetter = "get".concat(Author.class.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + Author.class.getDeclaredField(key).getName().substring(1));
                Method meth = Author.class.getDeclaredMethod(makeGetter);
                Author n = new Author(a);
                if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(meth.invoke(n))).find()) {
                    return n;
                }
            } catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                break;
            }
        }
        System.out.println("Search inconclusive");
        return null;

    }

    public List<String> makeListFromPath(Path precisePath) {

        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        return Database.makeListFromTxt(precisePath);
    }


    public void save(Object object, Path precisePath) {
        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        Database.save(object.toString(), precisePath);
    }

    private void verifyDirectoryIntegrity() {
        checkPath(path);
        checkPath(bookPath);
        checkPath(authorPath);
    }

    private void checkPath(Path path) {

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

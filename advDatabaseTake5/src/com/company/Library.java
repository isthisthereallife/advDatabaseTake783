package com.company;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Library {
    static Path path = Paths.get("databases/library/");
    static Path bookPath = Paths.get(path + "/books");
    static Path authorPath = Paths.get(path + "/authors");

    Library() {
        verifyDirectoryIntegrity();
        new LibraryMenu();

        Book b = new Book("book2", "2222222", "title2", "åthor", "tjanger", "2222");
        save(b, b.getPath());

        Book d = (Book) Search.findOne("genre", "jan", bookPath);
        if (b.getISBN().equals(d.getISBN())) {
            System.out.println("Same same: " + b.getISBN() + " == " + d.getISBN());
            save(d, d.getPath());
        }
        System.out.println(d.toString());
        Author a1 = new Author();
        save(a1, a1.getPath());
        Author a2 = (Author) Search.findOne("firstName", "first", authorPath);
        if (a1.getFirstName().equals(a2.getFirstName())) {
            System.out.println(a1.getFirstName() + " == " + a2.getFirstName());
        }
    }

    public void delete(Path precisePath) {
        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        Database.delete(precisePath);
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

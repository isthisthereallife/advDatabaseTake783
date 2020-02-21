package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Library {
    Path path = Paths.get("databases/library/");

    Library() {
        checkPath();

        Book b = new Book("the ID and also file name", "the ISBN","the title", "the authorID", "the genre","the year");
        save(b, b.path);

        Book d = (Book) findOne("title", "the title");

        System.out.println("Same same: "+b.ISBN + " == " + d.ISBN);
    }

    public Object findOne(String key, String val) {

        for (File f : Database.getFilesFromPath(path)) {
            ArrayList<String> a = (ArrayList<String>) Database.makeListFromTxt(f.toPath());
            try {
                Book.class.getDeclaredField(key);
                Book n = new Book(a);
                if (n.getTitle().matches(val)) {
                    return n;
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                //try (Field field = User.class.getDeclaredField(key);
            }
        }
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

    private void checkPath() {

        if (Files.exists(path)) {
            System.out.println("Path exists, all is well");

        } else { //skapa path om den inte finns
            System.out.println(path + " does not exist... creating...");
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

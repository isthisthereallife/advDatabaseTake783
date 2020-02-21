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

    Library() {
        verifyDirIntegrity();

        Book b = new Book("book2", "2222222", "title2", "åthor", "tjanger", "2222");
        save(b, b.path);

        Book d = (Book) findOne("genre", "jan", bookPath);
        if (b.ISBN.equals(d.ISBN))
            System.out.println("Same same: " + b.ISBN + " == " + d.ISBN);
    }

    public Object findOne(String key, String val, Path path) {
        for (File f : Database.getFilesFromPath(path)) {
            ArrayList<String> a = (ArrayList<String>) Database.makeListFromTxt(f.toPath());
            try {
                Book.class.getDeclaredField(key);
                String makeGetter = "get".concat(Book.class.getDeclaredField(key).getName().substring(0, 1).toUpperCase() + Book.class.getDeclaredField(key).getName().substring(1));
                Method m = Book.class.getDeclaredMethod(makeGetter);
                Book n = new Book(a);

                if (Pattern.compile(val, Pattern.CASE_INSENSITIVE).matcher(String.valueOf(m.invoke(n))).find()) {
                    System.out.println("HAHA!!!! GLORIOUS SUCCESS");
                    System.out.println(n.toString());
                    return n;
                }
            } catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                //try {User.class.getDeclaredField(key)
            }
        }
        return new Book();

    }

    public List<String> makeListFromPath(Path precisePath) {

        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        return Database.makeListFromTxt(precisePath);
    }


    public void save(Object object, Path precisePath) {
        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        Database.save(object.toString(), precisePath);
    }

    private void verifyDirIntegrity() {
        checkPath(path);
        checkPath(bookPath);
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

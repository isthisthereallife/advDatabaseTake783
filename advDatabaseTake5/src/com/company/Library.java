package com.company;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Library {
    Path path = Path.of("C:\\Users\\isthi\\Documents\\Skola\\Git\\Projects\\advDatabaseTake5\\databases/library/");

    Library() {
        checkPath();
        Book b = new Book();
        save(b, b.path);
        Book c = new Book(makeListFromPath(b.path));
        save(c,c.path);
        //läsa
        Book d = (Book) findOne("title", "testtitle1");
        System.out.println(b.ISBN + " == " + c.ISBN);
    }

    public Object findOne(String key, String val) {

        for (File f : Database.getFilesFromPath(path)) {
            ArrayList<String> a = (ArrayList<String>) Database.makeListFromTxt(f.toPath());


            if(a.get(0).startsWith(key)){
                try {
                    Field field = Book.class.getDeclaredField(key);
                    System.out.println("field = " + field);
                    //if(field.){
                        Book n = new Book(a);
                        if(n.getTitle().matches(val)){
                            return n;
                      //  }
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

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

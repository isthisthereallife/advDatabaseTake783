package com.company.lib;

import com.company.db.Database;
import com.company.db.Entity;
import com.company.db.Search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Library {
    static Path path = Paths.get("databases/library/");
    //add #newEntity here

    Library() {

        verifyDirectoryIntegrity();
        //new LibraryMenu();
        Book b2 = new Book("9oeu98", "I292223", "Thgok 3", "Authore", "Fantasy","1889");
        Book b = new Book("8a8ou98", "34329h942", "Thgok 4", "Authore", "Harlequin", "1918");
        Author au = new Author();
        Author ae = au;
        au.setAuthorID("Authore");
        au.updatePath();
        Database.save(ae);
        Database.save(au);
        Database.save(b2);
        //Database.save(b);
        Database.save(b);
        ArrayList<Object> a2 = Search.findMany("authorID","th",Author.class);
        ArrayList<Object> a = Search.findMany("title","th",Book.class);
        for(Object e : a){
            System.out.println(e.toString());

        }
        /*Book d = (Book) Search.findOne("genre", "jan", Book.getPath());
        if (b.getISBN().equals(d.getISBN())) {
            System.out.println("Same same: " + b.getISBN() + " == " + d.getISBN());
            d.setTitle("newTitle");
            save(d);
        }

        delete(b);


         */

        System.out.println("\nBooks created before yesterday: ");
        Search.printResult(Search.findMany("dateTimeCreated", LocalDateTime.now().minusDays(1).toString(), Book.class));
/*
        Author a1 = new Author();
        save(a1);
        if (a1.getFirstName().equals(a2.getFirstName())) {
            System.out.println(a1.getFirstName() + " == " + a2.getFirstName());
        }

 */
    }
    /*

     */
    @Deprecated
    public List<String> makeListFromPath(Path precisePath) {

        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        return Database.makeListFromTxt(precisePath);
    }


    //add #newEntity here
    private void verifyDirectoryIntegrity() {
        checkPath(path);
        checkPath(Book.getPath());
        checkPath(Author.getPath());
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

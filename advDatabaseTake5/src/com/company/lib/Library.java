package com.company.lib;

import com.company.db.Database;
import com.company.db.Entity;
import com.company.db.Search;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class Library {
    static Path path = Paths.get("databases/library/");


    Library() {
        Database.verifyDirectoryIntegrity(path);
        Database.verifyDirectoryIntegrity(Book.getPath());
        Database.verifyDirectoryIntegrity(Author.getPath());


        new SearchMenu();
        //if (Entity.class.)
        //for(Class c : Entity.class)

        System.out.print("Match all words? :");
        boolean strictSearch = true;
        if (new Scanner(System.in).nextLine().equalsIgnoreCase("yes")) {
            strictSearch = false;
        }


        //new LibraryMenu();
        Book b2 = new Book("9oeu98", "I292223", "Thgok 3", "Authore", "Fantasy", "1889");
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



        System.out.println("\nBooks created before yesterday: ");
        Search.printResult(Search.findMany("dateTimeCreated", LocalDateTime.now().minusDays(1).toString(), false,Book.class));

    }

    @Deprecated
    public List<String> makeListFromPath(Path precisePath) {

        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        return Database.makeListFromTxt(precisePath);
    }

}

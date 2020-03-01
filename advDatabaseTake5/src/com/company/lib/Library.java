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

    public Library() {
        Database db = Database.getInstance();
        db.verifyDirectoryIntegrity(Author.getPath());
        db.verifyDirectoryIntegrity(Book.getPath());

        //run this once to generate author and book files
        //new com.company.lib.dataGenerator.DataFactory();


        //create
        System.out.println("Create a book and an author");
        Book book = new Book("idOfNewBook", "isbn", "TestBook", "", "Realism", "1999");
        Author author = new Author("idOfNewAuthor", "Jo", "Tester");

        //add author to book
        book.setID(author.getID());
        //add book to author
        author.setBibliography(book.getID());
        //save
        db.save(book);
        db.save(author);

        System.out.println("\nfind one and print");
        Search.printResult(Search.findOne("ID", book.getID(), true, Book.class));
        System.out.println("find many and print (books added before yesteryear)");
        Search.printResult(Search.findMany("dateTimeCreated", LocalDateTime.now().minusYears(1).toString(), false, Book.class));


        //delete one
        System.out.println("Delete an author");
        Optional<Entity> au = com.company.db.Search.findOne("ID", author.getID(), true, Author.class);
        au.ifPresent(i -> {
            Author a = (Author) au.get();
            db.delete(a);
        });

        //delete many
        for (Entity e : com.company.db.Search.findMany("title", "destructive", true, Entity.class)) {
            db.delete(e);
        }

        //same search after deletion
        System.out.println("Same Search after deletion");
        Search.printResult(Search.findOne("ID", author.getID(), true, Author.class));

        new LibraryMenu();

    }

    @Deprecated
    public List<String> makeListFromPath(Path precisePath) {

        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        return Database.getInstance().makeListFromTxt(precisePath);
    }


}

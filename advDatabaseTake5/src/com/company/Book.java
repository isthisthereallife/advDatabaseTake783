package com.company;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Book {
    String bookID;
    String ISBN;
    String title;
    String authorID;
    String genre;
    String year;
    Path path = Paths.get("books/");


    Book() {
        this.bookID = "no id";
        this.ISBN = "no isbn";
        this.title = "no title";
        this.authorID = "no author";
        this.genre = "no genre";
        this.year = "no year";
        this.path = Paths.get(path.toString() + "/" + this.bookID + ".txt");
    }

    Book(List<String> s) {
        this.bookID = s.get(0).substring(6).trim();
        this.ISBN = s.get(1).substring(4).trim();
        this.title = s.get(2).substring(5).trim();
        this.authorID = s.get(3).substring(8).trim();
        this.genre = s.get(4).substring(5).trim();
        this.year = s.get(5).substring(4).trim();
        this.path = Paths.get(path.toString() + "/" + this.bookID + ".txt");
    }


    Book(String bookID, String ISBN, String title, String authorID, String genre, String year) {
        this.bookID = bookID;
        this.ISBN = ISBN;
        this.title = title;
        this.authorID = authorID;
        this.genre = genre;
        this.year = year;
        this.path = Paths.get(path.toString() + "/" + this.bookID + ".txt");
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public String toString() {
        return "bookID " + bookID + "\nISBN " + ISBN + "\ntitle " + title +
                "\nauthorID " + authorID + "\ngenre " + genre + "\nyear " + year + "\npath " + path;
    }

}

package com.company.lib;

import com.company.db.Entity;
import com.company.db.Search;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;


public class Book implements Entity {
    @NotNull
    private String ID;
    private String ISBN;
    private String title;
    private String authorID;
    private String genre;
    private String year;
    private Path path;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeAccessed;


    public Book() {
        this.ID = "no ID";
        this.ISBN = "no isbn";
        this.title = "no title";
        this.authorID = "no author";
        this.genre = "no genre";
        this.year = "no year";
        this.path = Paths.get(this.ID + ".txt");
        this.dateTimeCreated = LocalDateTime.now();
        this.dateTimeAccessed = LocalDateTime.now();
    }

    public Book(List<String> s) {
        this.ID = s.get(0).trim();
        this.ISBN = s.get(1).trim();
        this.title = s.get(2).trim();
        this.authorID = s.get(3).trim();
        this.genre = s.get(4).trim();
        this.year = s.get(5).trim();
        this.path = Paths.get(this.ID + ".txt");
        this.dateTimeCreated = LocalDateTime.parse(s.get(7).trim());
        this.dateTimeAccessed = LocalDateTime.now();
    }


    public Book(String ID, String ISBN, String title, String authorID, String genre, String year) {
        this.ID = ID;
        this.ISBN = ISBN;
        this.title = title;
        this.authorID = authorID;
        this.genre = genre;
        this.year = year;
        this.path = Paths.get(this.ID + ".txt");
        this.dateTimeCreated = LocalDateTime.now();
        this.dateTimeAccessed = LocalDateTime.now();
    }

    public String getID() {
        return ID;
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public void setID(String ID) {
        this.ID = ID;
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
        return ID + "\n" + ISBN + "\n" + title +
                "\n" + authorID + "\n" + genre + "\n" + year + "\n" + path + "\n" + dateTimeCreated + "\n" + dateTimeAccessed;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public LocalDateTime getDateTimeAccessed() {
        return dateTimeAccessed;
    }

    @Override
    public Path getPathWithId() {
        return this.path;
    }

    public static Path getPath() {
        return Library.path.resolve(Book.class.getSimpleName().substring(0, 1).toLowerCase().concat(Book.class.getSimpleName().substring(1) + "s"));
    }

    public String toPrettyString() {

        String authorName = "unknown";
        Optional<Entity> ent = Search.findOne("ID", authorID, true, Author.class);
        if (ent.isPresent()) {
            Author a = (Author) ent.get();
            authorName = a.getFirstName() + " " + a.getLastName();
        }
        return String.format("~~~~~~~~~~~~~~%nTitle: %s%nAuthor: %s%nGenre: %s%nYear: %s%nDate added: %s%nDate accessed: %s%n~~~~~~~~~~~~~~", title,
                authorName, genre, year,
                dateTimeCreated.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)),
                dateTimeAccessed.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
    }

}

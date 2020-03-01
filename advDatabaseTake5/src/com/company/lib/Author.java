package com.company.lib;

import com.company.db.Entity;
import com.company.db.Search;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Author implements Entity {
    @NotNull
    private String ID;
    private String firstName;
    private String lastName;
    private String bibliography;
    private Path path;


    public Author() {
        this.ID = "no id";
        this.firstName = "no first name";
        this.lastName = "no last name";
        this.bibliography = "";
        this.path = Paths.get(this.ID + ".txt");
    }

    public Author(List<String> s) {
        this.ID = s.get(0).trim();
        this.firstName = s.get(1).trim();
        this.lastName = s.get(2).trim();
        this.bibliography = s.get(3).trim();
        this.path = Paths.get(this.ID + ".txt");
    }

    public Author(String ID, String firstName, String lastName) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.path = Paths.get(this.ID + ".txt");
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }


    public String toString() {
        return ID + "\n" + firstName + "\n" + lastName +
                "\n" + bibliography + "\n" + path;
    }

    @Override
    public Path getPathWithId() {
        return this.path;
    }

    public static Path getPath() {
        return Path.of(Library.path + "/authors");
    }

    public void updatePath() {
        this.path = Path.of(this.ID + ".txt");
    }

    public String toPrettyString() {
        String bibliography = "";
        ArrayList<Entity> a = Search.findMany("authorID", ID, true, Book.class);
        for (Entity ent : a) {
            bibliography = bibliography.concat(((Book) ent).getTitle() + " (" + ((Book) ent).getYear() + ")\n");
        }

        return String.format("~~~~~~~~~~~~~~%nName: %s %s%nBibliography: %n%s~~~~~~~~~~~~~~", firstName, lastName, bibliography);
    }
}

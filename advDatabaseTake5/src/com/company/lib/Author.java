package com.company.lib;

import com.company.db.Entity;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Author implements Entity {
    private String authorID;
    private String firstName;
    private String lastName;
    private String bibliography;
    private Path path;


    public Author() {
        this.authorID = "no id";
        this.firstName = "no first name";
        this.lastName = "no last name";
        this.bibliography = "no bibliography";
        this.path = Paths.get(this.authorID+".txt");
    }

    public Author(List<String> s) {
        this.authorID = s.get(0).trim();
        this.firstName = s.get(1).trim();
        this.lastName = s.get(2).trim();
        this.bibliography = s.get(3).trim();
        this.path = Paths.get(this.authorID+".txt");
    }

    public String getAuthorID() {
        return authorID;
    }

    public void setAuthorID(String authorID) {
        this.authorID = authorID;
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


    public String toString(){
        return authorID+"\n"+firstName+"\n"+lastName+
                "\n"+bibliography+"\n"+path;
    }

    @Override
    public Path getPathWithId() {
        return this.path;
    }

    public static Path getPath() {
        return Path.of(Library.path + "/authors");
    }

    public void updatePath() {
        this.path = Path.of(this.authorID+".txt");
    }
}

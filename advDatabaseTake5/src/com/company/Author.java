package com.company;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Author {
    private String authorID;
    private String firstName;
    private String lastName;
    private String bibliography;
    private Path path = Paths.get("authors/");

    Author() {
        this.authorID = "no id";
        this.firstName = "no first name";
        this.lastName = "no last name";
        this.bibliography = "no bibliography";
        this.path = Paths.get(path.toString() + "/" + this.authorID);
    }

    Author(List<String> s) {
        this.authorID = s.get(0).substring(8).trim();
        this.firstName = s.get(1).substring(10).trim();
        this.lastName = s.get(2).substring(9).trim();
        this.bibliography = s.get(3).substring(12).trim();
        this.path = Paths.get(path.toString() + "/" + this.authorID);
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
        return "authorID "+authorID+"\nfirstName "+firstName+"\nlastName "+lastName+
                "\nbibliography "+bibliography+"\npath "+path;
    }

    public Path getPath() {
        return this.path;
    }
}

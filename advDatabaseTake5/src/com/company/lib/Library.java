package com.company.lib;

import com.company.db.Database;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Library {
    static Path path = Paths.get("databases/library/");

    Library() {
        Database.verifyDirectoryIntegrity(path);
        Database.verifyDirectoryIntegrity(Book.getPath());
        Database.verifyDirectoryIntegrity(Author.getPath());

        new LibraryMenu();
        new SearchMenu();

    }

    @Deprecated
    public List<String> makeListFromPath(Path precisePath) {

        precisePath = Path.of(path.toString() + "/" + precisePath.toString());
        return Database.makeListFromTxt(precisePath);
    }

}

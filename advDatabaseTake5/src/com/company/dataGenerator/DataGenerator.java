package com.company.dataGenerator;

import com.company.db.Database;
import com.company.lib.Author;
import com.company.lib.Book;

import java.nio.file.Path;
import java.util.ArrayList;

@Deprecated
public class DataGenerator {
    public DataGenerator() {
        makeData();

    }

    void makeData() {
        ArrayList<String> allBooks = (ArrayList<String>) Database.makeListFromTxt(Path.of("mock_data/mock_books.csv"));
        ArrayList<String> allAuthors = (ArrayList<String>) Database.makeListFromTxt(Path.of("mock_data/mock_authors.csv"));

        int i = 0;
        for (String s : allBooks) {
            String[] a = allAuthors.get(i).split(",");
            Author au = new Author(a[0], a[1], a[2]);
            String[] b = s.split(",");
            Book bo = new Book(b[0], b[1], b[2], au.getAuthorID(), b[3], b[4]);
            au.setBibliography(bo.getBookID());
            Database.save(au);
            Database.save(bo);

            i++;
        }
        System.out.println("Done importing.");
    }
}

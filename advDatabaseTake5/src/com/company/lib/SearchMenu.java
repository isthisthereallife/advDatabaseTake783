package com.company.lib;

import com.company.db.Search;

import java.time.LocalDateTime;
import java.util.Scanner;

public class SearchMenu {
    SearchMenu() {
        String choice = "";
        String val = "";
        String key = "";
        Scanner input = new Scanner(System.in);
        do {

            System.out.println("Search for what?\n\n1. Authors by last name\n2. Books by title\n3. Books added before today\n4. DIY-search\n0. Cancel");
            choice = input.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Authors by last name.\nPlease enter query: ");
                    val = input.nextLine();
                    Search.printResult(Search.findMany("lastName", val, false, Author.class));
                    break;
                case "2":
                    System.out.print("Books by title.\nPlease enter query: ");
                    val = input.nextLine();
                    Search.printResult(Search.findMany("title", val, false, Book.class));
                    break;
                case "3":
                    Search.printResult(Search.findMany("dateTimeCreated", LocalDateTime.now().minusDays(1).toString(), false, Book.class));
                    break;
                case "4":
                    diySearch();
                case "0":
                    break;
                default:
                    System.out.println("Try again.\n");
            }
        } while (!choice.equals("0"));
    }

    void diySearch() {
        System.out.print("Search what field? : ");
        String field = new Scanner(System.in).nextLine();
        if (field.equalsIgnoreCase("author")) field = "authorID";

        System.out.print("Search where? ");
        Class c = null;
        String klass = new Scanner(System.in).nextLine();
        klass = klass.substring(0, 1).toUpperCase().concat(klass.substring(1));
        if (klass.equalsIgnoreCase(Book.class.getSimpleName()) || klass.replace("s", "").equalsIgnoreCase(Book.class.getSimpleName())) {
            c = Book.class;
        } else if (klass.equalsIgnoreCase(Author.class.getSimpleName()) || klass.replace("s", "").equalsIgnoreCase(Author.class.getSimpleName())) {
            c = Author.class;
        }

        System.out.print("Search for what? ");
        String query = new Scanner(System.in).nextLine();

        System.out.print("Match all words? ");
        boolean strictSearch = false;
        if (new Scanner(System.in).nextLine().equalsIgnoreCase("yes")) {
            strictSearch = true;
        }

        System.out.print("Find one or many? ");
        if (new Scanner(System.in).nextLine().equalsIgnoreCase("one")) {
            Search.printResult(Search.findOne(field, query, strictSearch, c));
        } else {
            Search.printResult(Search.findMany(field, query, strictSearch, c));
        }
    }

}

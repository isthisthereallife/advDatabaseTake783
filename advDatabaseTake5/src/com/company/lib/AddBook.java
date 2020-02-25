package com.company.lib;

import com.company.db.Entity;
import com.company.db.Search;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AddBook {
    AddBook() {
        boolean foundAuthor = false;
        String firstName;
        String lastName;
        Author a = new Author();
        int choice = 99999;


        Scanner s = new Scanner(System.in);

        System.out.println("Add to existing author?");
        if (s.nextLine().equalsIgnoreCase("no")) {
            System.out.println("Enter first name of author: ");
            firstName = s.nextLine();
            System.out.println("Enter last name of author: ");
            lastName = s.nextLine();
            a.setFirstName(firstName);
            a.setLastName(lastName);
            a.setAuthorID(firstName + lastName);

        } else {
            do {
                System.out.print("Search for author by last name: ");
                ArrayList<Entity> entityArrayList = Search.findMany("lastName", s.nextLine(), false, Author.class);
                System.out.println("~~~~~~~~~~~~~~\nAuthors matching your search query: ");
                for (int i = 0, entityArrayListSize = entityArrayList.size(); i < entityArrayListSize; i++) {
                    Author author = (Author) entityArrayList.get(i);
                    System.out.println(i + ". " + author.getFirstName() + " " + author.getLastName());
                }
                if (entityArrayList.size() > 0) {
                    do {
                        System.out.print("~~~~~~~~~~~~~~\nChoose author from list: ");
                        try {
                            choice = s.nextInt();
                            s.nextLine();
                        } catch (InputMismatchException e) {
                            choice = -1;
                            s.nextLine();
                            System.out.println("Try again. Enter a number from the list.");
                        }
                    } while (choice < 0 || choice > entityArrayList.size()-1);


                    a = (Author) entityArrayList.get(choice);
                    foundAuthor = true;
                } else System.out.println("None. Try again.\n~~~~~~~~~~~~~~");

            }while(!foundAuthor);
        }
        System.out.print("Enter title: ");
        String title = s.nextLine();
        System.out.print("Enter genre: ");
        String genre = s.nextLine();
        System.out.print("Enter year published: ");
        String year = s.nextLine();
        System.out.print("Enter ISBN: ");
        String ISBN = s.nextLine();

        a.setBibliography(a.getBibliography().concat(" " + ISBN));
        a.updatePath();
        com.company.db.Database.save(a);
        Book b = new Book(ISBN, ISBN, title, a.getAuthorID(), genre, year);
        com.company.db.Database.save(b);
        System.out.println("Well done. Book added.");


    }
}

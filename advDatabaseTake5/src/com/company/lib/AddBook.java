package com.company.lib;

import com.company.db.Entity;
import com.company.db.Search;

import java.util.ArrayList;
import java.util.Scanner;

public class AddBook {
    AddBook(){
        String firstName;
        String lastName;
        Author a= new Author();
        int choice = 99999;



        Scanner s = new Scanner(System.in);

        System.out.println("Add to existing author?");
        if (s.nextLine().equalsIgnoreCase("no")){
            System.out.println("Enter first name of author: ");
            firstName = s.nextLine();
            System.out.println("Enter last name of author: ");
            lastName = s.nextLine();
            a.setFirstName(firstName);
            a.setLastName(lastName);
            a.setAuthorID(firstName+lastName);

        }
        else {
            System.out.print("Search for author by last name: ");
            ArrayList<Entity> entityArrayList = Search.findMany("lastName", s.nextLine(), false, Author.class);
            System.out.println("Choose author from list: ");
            for (int i = 0, entityArrayListSize = entityArrayList.size(); i < entityArrayListSize; i++) {
                Author author = (Author) entityArrayList.get(i);
                System.out.println(i + ". " + author.getFirstName() + " " + author.getLastName());
            }
            do {
                try {
                    choice = s.nextInt();
                    s.nextLine();
                } catch (NumberFormatException e) {
                    System.out.println("Try again. Enter a number from the list.");
                }
            } while (choice < entityArrayList.size()-1 || choice > entityArrayList.size());

            a = (Author) entityArrayList.get(choice);
        }
            System.out.print("Enter title: ");
            String title = s.nextLine();
            System.out.print("Enter genre: ");
            String genre = s.nextLine();
            System.out.print("Enter year published: ");
            String year  = s.nextLine();
            System.out.print("Enter ISBN: ");
            String ISBN = s.nextLine();

            a.setBibliography(a.getBibliography().concat(" "+ISBN));
            a.updatePath();
            com.company.db.Database.save(a);
            Book b = new Book(ISBN,ISBN,title,a.getAuthorID(),genre,year);
            com.company.db.Database.save(b);
            System.out.println("Well done. Book added.");


    }
}

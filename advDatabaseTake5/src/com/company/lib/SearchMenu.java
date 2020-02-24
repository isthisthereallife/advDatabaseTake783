package com.company.lib;

import com.company.db.Search;

import java.time.LocalDateTime;
import java.util.Scanner;

public class SearchMenu {
    SearchMenu(){
        String choice="";
        String val = "";
        String key = "";
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("Search for what?\n\n1. Authors by last name\n2. Books by title\n3. Books added before today\n0. Cancel");
            choice = input.nextLine();
            switch (choice) {
                case "1":
                    System.out.print("Please enter query: ");
                    val = input.nextLine();
                    Search.findMany("lastName",val,Author.class);
                    break;
                case "2":
                    System.out.print("Please enter query: ");
                    val = input.nextLine();
                    Search.printResult(Search.findMany("title",val,Book.class));
                    break;
                case "3":
                    Search.printResult(Search.findMany("dateTimeCreated", LocalDateTime.now().minusDays(1).toString(),Book.class));
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Try again.\n");
            }
        }while (!choice.equals("0"));
    }

}

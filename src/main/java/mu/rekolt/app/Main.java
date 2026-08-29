package mu.rekolt.app;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Locale;

public class Main {
        public static void main(String[] args) {
                //We added initial variable declarations to store user menu selection and pricing state
                int choice = 0;
                int price;
        do { // do-while loop to keep displaying the application menu until the user chooses to exit
                do
                        System.out.println();
                        System.out.println("=== Welcome to REKOLT Planters Cooperative | Produce Tracking System 2026 ===\"");
                        System.out.println("_________________________________________________________________________\n");
                        System.out.println("Main Menu");
                        System.out.println("_________ \n");



                        // We added a text block output to display the available main menu options
                        System.out.println("""
                    1. Log New Delivery
                    2. View Seasonal Values
                    3. Export Season Summary Report
                    4. Populate Sample Dataset
                    5. Exit
                    """);

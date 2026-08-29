package mu.rekolt.app;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
                int choice = 0;
                int price;
        do {
                        System.out.println();
                        System.out.println("=== Welcome to REKOLT Planters Cooperative | Produce Tracking System 2026 ===\"");
                        System.out.println("_________________________________________________________________________\n");
                        System.out.println("Main Menu");
                        System.out.println("_________ \n");
                        System.out.println("""
                    1. Log New Delivery
                    2. View Seasonal Values
                    3. Export Season Summary Report
                    4. Import Sample Dataset
                    5. Exit
                    """);

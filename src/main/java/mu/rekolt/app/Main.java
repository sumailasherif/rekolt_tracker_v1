package mu.rekolt.app;

import mu.rekolt.model.Delivery;
import mu.rekolt.service.ActivityLogger;
import mu.rekolt.service.DemoDataSeeder;
import mu.rekolt.service.ReportExporter;
import mu.rekolt.service.SeasonService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) {
                //I added initial variable declarations to store user menu selection and shared season state
                int choice = 0;
                Scanner scanner = new Scanner(System.in);
                SeasonService seasonService = new SeasonService();

                do { // do-while loop to keep displaying the application menu until the user chooses to exit
                        System.out.println();
                        System.out.println("=== Welcome to REKOLT Planters Cooperative | Produce Tracking System 2026 ===");
                        System.out.println("_________________________________________________________________________\n");
                        System.out.println("Main Menu");
                        System.out.println("_________ \n");

                        // I added a text block output to display the available main menu options
                        System.out.println("""
                    1. Log New Delivery
                    2. View Seasonal Values
                    3. Export Season Summary Report
                    4. Populate Sample Dataset
                    5. Exit
                    """);

                        System.out.print("Enter your choice: ");
                        try {
                                choice = scanner.nextInt();
                                scanner.nextLine(); // consume trailing newline left by nextInt()
                        } catch (InputMismatchException e) {
                                System.out.println("Please enter a number between 1 and 5.");
                                scanner.nextLine();
                                choice = 0;
                                continue;
                        }

                        switch (choice) {
                                case 1 -> Delivery.recordDelivery(scanner, seasonService);
                                case 2 -> {
                                        seasonService.printMemberTotals();
                                        seasonService.printWeeklyGrid();
                                }
                                case 3 -> {
                                        new ReportExporter().generateSeasonReport(seasonService);
                                        ActivityLogger.append(seasonService.getSortedMembers().size());
                                }
                                case 4 -> DemoDataSeeder.populateInitialData(seasonService);
                                case 5 -> System.out.println("Goodbye!");
                                default -> System.out.println("Please enter a number between 1 and 5.");
                        }
                } while (choice != 5);

                scanner.close();
        }
}
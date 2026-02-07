package com.company;

import com.company.controllers.interfaces.IPharmacyController;
import java.util.Scanner;

public class MyApplication {
    private final IPharmacyController controller;
    private final Scanner scanner = new Scanner(System.in);

    public MyApplication(IPharmacyController controller) {
        this.controller = controller;
    }

    public void start() {
        while (true) {
            try {
                System.out.println("\n=== PHARMACY MANAGEMENT SYSTEM ===");
                System.out.println("Select your role:");
                System.out.println("1. Admin");
                System.out.println("2. Manager");
                System.out.println("3. Pharmacist");
                System.out.println("4. Customer");
                System.out.println("0. Exit");
                System.out.print("Choice: ");

                int role = scanner.nextInt();

                if (role == 0) break;

                handleLogin(role);

            } catch (Exception e) {

                System.out.println("Error: Invalid input format. Please use numbers where required.");

                scanner.nextLine();
            }
        }
    }

    private void handleLogin(int role) {
        if (role == 4) {
            customerMenu();
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.next();
        switch (role) {

            case 1 -> {
                if (password.equals("admin123")) adminMenu(role);
                else System.out.println("Wrong admin password!");
            }

            case 2 -> {

                if (password.equals("man123")) managerMenu();
                else System.out.println("Wrong manager password!");
            }

            case 3 -> {

                if (password.equals("staff123")) staffMenu();
                else System.out.println("Wrong staff password!");
            }

            default -> System.out.println("Invalid role!");
        }
    }

    private void adminMenu(int role) {
        while (true) {

            System.out.println("\n[ADMIN MENU]");
            System.out.println("1. Add Medicine\n2. Show All Medicines\n3. View Sales History\n4. Show Low Stock\n0. Logout");

            int choice = scanner.nextInt();

            if (choice == 1) addMedicineMenu(role);

            else if (choice == 2) System.out.println(controller.showAllMedicines());

            else if (choice == 3) System.out.println(controller.getSalesHistory());

            else if (choice == 4) System.out.println(controller.getLowStockMedicines());

            else if (choice == 0) break;
        }
    }

    private void managerMenu() {
        while (true) {
            System.out.println("\n[MANAGER MENU]");
            System.out.println("1. Inventory Report");
            System.out.println("2. Change Medicine Price"); // Пункт 2 теперь работает
            System.out.println("3. Show Low Stock");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            if (choice == 1) {
                System.out.println(controller.showAllMedicines());
            }
            else if (choice == 2) {
                System.out.print("Enter Medicine ID: ");
                int id = scanner.nextInt();
                System.out.print("Enter New Price: ");
                double newPrice = scanner.nextDouble();
                System.out.println(controller.updatePrice(id, newPrice));
            }
            else if (choice == 3) {
                System.out.println(controller.getLowStockMedicines());
            }
            else if (choice == 0) break;
        }
    }


    private void staffMenu() {

        while (true) {

            System.out.println("\n[PHARMACIST MENU]");

            System.out.println("1. Show All Medicines\n2. Sell Medicine\n0. Logout");

            int choice = scanner.nextInt();

            if (choice == 1) System.out.println(controller.showAllMedicines());

            else if (choice == 2) sellMedicineMenu();

            else if (choice == 0) break;

        }

    }


    private void customerMenu() {

        while (true) {

            System.out.println("\n[CUSTOMER AREA]");

            System.out.println("1. View Available Medicines\n0. Exit to main menu");

            int choice = scanner.nextInt();

            if (choice == 1) System.out.println(controller.showAllMedicines());

            else if (choice == 0) break;

        }

    }


    private void addMedicineMenu(int userRole) {

        scanner.nextLine();


        System.out.print("Name (can include spaces): ");

        String name = scanner.nextLine();


        System.out.print("Price: ");

        double price = scanner.nextDouble();

        scanner.nextLine();


        System.out.print("Manufacturer: ");

        String manufacturer = scanner.nextLine();


        System.out.print("Quantity: ");

        int quantity = scanner.nextInt();


        System.out.print("Category ID (1 - General, 2 - Painkillers): ");

        int categoryId = scanner.nextInt();


        System.out.print("Requires prescription? (yes/no): ");

        boolean prescription = scanner.next().equalsIgnoreCase("yes");



        System.out.println(

                controller.addMedicine(name, price, manufacturer, quantity, prescription, userRole)

        );

    }


    private void sellMedicineMenu() {

        System.out.print("Medicine name: ");

        String name = scanner.next();


        System.out.print("Quantity: ");

        int qty = scanner.nextInt();


        System.out.print("Do you have a prescription? (yes/no): ");

        boolean hasPrescription =

                scanner.next().equalsIgnoreCase("yes");


        System.out.println(

                controller.sellMedicine(name, qty, hasPrescription)

        );

    }
}
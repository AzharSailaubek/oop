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
            System.out.println("\n=== PHARMACY MANAGEMENT SYSTEM ===");
            System.out.println("Select your role:");
            System.out.println("1. Admin");
            System.out.println("2. Manager");
            System.out.println("3. Pharmacist (Staff)");
            System.out.println("4. Customer");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int role = scanner.nextInt();
            if (role == 0) break;

            handleLogin(role);
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
                if (password.equals("admin123")) adminMenu();
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

    private void adminMenu() {
        while (true) {
            System.out.println("\n[ADMIN MENU]");
            System.out.println("1. Add Medicine\n2. Show All Medicines\n3. View Sales History (JOIN Report)\n0. Logout");
            int choice = scanner.nextInt();
            if (choice == 1) addMedicineMenu();
            else if (choice == 2) System.out.println(controller.showAllMedicines());
            else if (choice == 3) System.out.println(controller.getSalesHistory());
            else if (choice == 0) break;
        }
    }

    private void managerMenu() {
        while (true) {
            System.out.println("\n[MANAGER MENU]");
            System.out.println("1. Show Inventory\n2. View Sales History\n0. Logout");
            int choice = scanner.nextInt();
            if (choice == 1) System.out.println(controller.showAllMedicines());
            else if (choice == 2) System.out.println(controller.getSalesHistory());
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

    private void addMedicineMenu() {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        System.out.print("Manufacturer: ");
        String manufacturer = scanner.next();

        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Category (e.g., Antibiotics): ");
        String category = scanner.next();

        System.out.print("Requires prescription? (yes/no): ");
        boolean prescription =
                scanner.next().equalsIgnoreCase("yes");

        System.out.println(
                controller.addMedicine(name, price, manufacturer, quantity, prescription)
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
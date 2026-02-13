package com.company;

import com.company.controllers.interfaces.IPharmacyController;
import com.company.models.User;
import com.company.repositories.interfaces.IUserRepository;
import java.util.Scanner;

public class MyApplication {
    private final IPharmacyController controller;
    private final IUserRepository userRepo;
    private final Scanner scanner = new Scanner(System.in);

    public MyApplication(IPharmacyController controller, IUserRepository userRepo) {
        this.controller = controller;
        this.userRepo = userRepo;
    }

    public void start() {
        while (true) {
            try {
                System.out.println("\n=== PHARMACY MANAGEMENT SYSTEM ===");
                System.out.println("Select your role to Login:");
                System.out.println("1. Admin");
                System.out.println("2. Manager");
                System.out.println("3. Pharmacist");
                System.out.println("4. Customer (No login required)");
                System.out.println("0. Exit");
                System.out.print("Choice: ");

                int selectedRole = scanner.nextInt();
                if (selectedRole == 0) break;

                handleLogin(selectedRole);

            } catch (Exception e) {
                System.out.println("Error: Invalid input. Please use numbers.");
                scanner.nextLine();
            }
        }
    }

    private void handleLogin(int selectedRole) {
        if (selectedRole == 4) {
            customerMenu();
            return;
        }

        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        User user = userRepo.authenticate(username, password);

        if (user != null && user.getRoleId() == selectedRole) {
            System.out.println("\nWelcome, " + user.getUsername() + "!");

            switch (user.getRoleId()) {
                case 1 -> adminMenu(user.getRoleId());
                case 2 -> managerMenu();
                case 3 -> staffMenu();
                default -> System.out.println("Role not recognized.");
            }
        } else {
            System.out.println("\n[!] ACCESS DENIED: Invalid credentials or role mismatch.");
        }
    }

    private void adminMenu(int role) {
        while (true) {
            System.out.println("\n[ADMIN MENU]");
            System.out.println("1. Add Medicine\n2. Archive Medicine\n3. Show All\n4. Sales History\n0. Logout");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) addMedicineMenu(role);
            else if (choice == 2) {
                System.out.print("Enter ID to DELETE: ");
                int id = scanner.nextInt();
                System.out.println(controller.removeMedicine(id, role));
            }
            else if (choice == 3) System.out.println(controller.showAllMedicines());
            else if (choice == 4) System.out.println(controller.getSalesHistory());
            else if (choice == 0) break;
        }
    }

    private void managerMenu() {
        while (true) {
            System.out.println("\n[MANAGER MENU]");
            System.out.println("1. Inventory Report\n2. Change Price\n3. Low Stock Alert\n4. Total Revenue\n0. Logout");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) System.out.println(controller.showAllMedicines());
            else if (choice == 2) {
                System.out.print("Enter ID: ");
                int id = scanner.nextInt();
                System.out.print("New Price: ");
                double price = scanner.nextDouble();
                System.out.println(controller.updatePrice(id, price));
            }
            else if (choice == 3) System.out.println(controller.getLowStockMedicines());
            else if (choice == 4) System.out.println(controller.getRevenueReport());
            else if (choice == 0) break;
        }
    }

    private void staffMenu() {
        while (true) {
            System.out.println("\n[PHARMACIST MENU]");
            System.out.println("1. Show All Medicines\n2. Sell Medicine\n0. Logout");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            if (choice == 1) System.out.println(controller.showAllMedicines());
            else if (choice == 2) sellMedicineMenu();
            else if (choice == 0) break;
        }
    }

    private void customerMenu() {
        while (true) {
            System.out.println("\n[CUSTOMER AREA]");
            System.out.println("1. View Available Medicines\n0. Back");
            System.out.print("Choice: ");
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
        scanner.nextLine(); // Очистка буфера ПОСЛЕ ввода числа перед текстом

        System.out.print("Manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Category ID: ");
        int categoryId = scanner.nextInt();

        System.out.print("Requires prescription? (yes/no): ");
        boolean prescription = scanner.next().equalsIgnoreCase("yes");

        System.out.println(controller.addMedicine(name, price, manufacturer, quantity, prescription, userRole));
    }

    private void sellMedicineMenu() {
        System.out.print("Enter Medicine ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter Quantity: ");
        int qty = scanner.nextInt();

        System.out.print("Does the customer have a prescription? (yes/no): ");
        boolean hasPrescription = scanner.next().equalsIgnoreCase("yes");

        System.out.println(controller.sellMedicine(id, qty, hasPrescription));
    }
}
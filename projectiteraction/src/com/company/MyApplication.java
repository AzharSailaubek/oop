package com.company;

import com.company.controllers.interfaces.IPharmacyController;

import java.util.Scanner;

public class MyApplication {
    private final IPhatrmacyController controller;
    private final Scanner scanner = new Scanner(System.in);

    public MyApplication(IPharmacyController controller) {
        this.controller = controller;
    }

    private void menu() {
        System.out.println("""
                1. Add medicine
                2. Show all medicines
                3. Sell medicine
                0. Exit
                """);
        System.out.print("Choose option: ");
    }

    public void start() {
        while (true) {
            menu();
            int option = scanner.nextInt();

            switch (option) {
                case 1 -> addMedicineMenu();
                case 2 -> System.out.println(controller.showAllMedicines());
                case 3 -> sellMedicineMenu();
                default -> {
                    return;
                }
            }
        }
    }

    private void addMedicineMenu() {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();

        System.out.print("Requires prescription? (yes/no): ");
        boolean prescription =
                scanner.next().equalsIgnoreCase("yes");

        System.out.println(
                controller.addMedicine(name, price, quantity, prescription)
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
                controller.sellMedicineByName(name, qty, hasPrescription)
        );
    }
}
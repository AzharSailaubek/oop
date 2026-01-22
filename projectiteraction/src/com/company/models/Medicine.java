package com.company.models;

public class Medicine {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private boolean requiresPrescription;

    public Medicine() {

    }

    public Medicine(String name, double price, int quantity, boolean requiresPrescription) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.requiresPrescription = requiresPrescription;
    }
    public Medicine(int id, String name, double price, int quantity, boolean requiresPrescription) {
        this(name, price, quantity, requiresPrescription);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isRequiresPrescription() {
        return requiresPrescription;
    }

    public void setQuantity(int quantity) {

        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id + " | " + name +
                " | price: " + price +
                " | qty: " + quantity +
                " | prescription: " +
                (requiresPrescription ? "yes" : "no");
    }
}


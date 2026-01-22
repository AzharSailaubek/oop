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



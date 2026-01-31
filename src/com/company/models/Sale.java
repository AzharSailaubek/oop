package com.company.models;

import java.time.LocalDate;

public class Sale {
    private int id;
    private int medicineId;
    private int quantity;
    private double totalPrice;
    private LocalDate saleDate;

    public Sale(int medicineId, int quantity, double totalPrice) {
        this.medicineId = medicineId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleDate = LocalDate.now();
    }

    public int getMedicineId() {
        return medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }
}
package com.company.models;

public class Medicine {
    private int id;
    private String name;
    private double price;
    private String manufacturer;
    private int quantity;
    private boolean prescriptionRequired;

    public Medicine() {
    }

    public Medicine(String name, double price, String manufacturer,  int quantity, boolean prescriptionRequired) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.prescriptionRequired = prescriptionRequired;
    }
    public Medicine(int id, String name, double price,String manufacturer, int quantity, boolean prescriptionRequired) {
        this(name, price,manufacturer, quantity, prescriptionRequired);
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

    public String getManufacturer() { return manufacturer; }

    public int getQuantity() {
        return quantity;
    }

    public boolean isPrescriptionRequired() {
        return prescriptionRequired;
    }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setQuantity(int quantity) {this.quantity = quantity;}
    public void setPrescriptionRequired(boolean prescriptionRequired) { this.prescriptionRequired = prescriptionRequired; }

    @Override
    public String toString() {
        return id + " | " + name +
                " | price: " + price +
                " | qty: " + quantity +
                " | prescription: " +
                (prescriptionRequired ? "yes" : "no");
    }
}


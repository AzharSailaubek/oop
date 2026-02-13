package com.company.models;
public class Medicine {

    private int id;
    private String name;
    private double price;
    private String manufacturer;
    private int quantity;
    private boolean prescriptionRequired;
    private int categoryId;
    private String categoryName;
    public Medicine() {

    }
    public Medicine(String name, double price, String manufacturer, int quantity, boolean prescriptionRequired, int categoryId) {
        this.name = name;
        this.price = price;
        this.manufacturer = manufacturer;
        this.quantity = quantity;
        this.prescriptionRequired = prescriptionRequired;
        this.categoryId = categoryId;
    }

    public Medicine(int id, String name, double price, String manufacturer, int quantity, boolean prescriptionRequired, int categoryId) {
        this(name, price, manufacturer, quantity, prescriptionRequired, categoryId);
        this.id = id;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean isPrescriptionRequired() { return prescriptionRequired; }
    public void setPrescriptionRequired(boolean prescriptionRequired) { this.prescriptionRequired = prescriptionRequired; }

    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    @Override
    public String toString() {
        return String.format("%d | %-15s [%-12s] | Price: %.2f | Qty: %d | Prescription: %s",
                id, name, (categoryName != null ? categoryName : categoryId),
                price, quantity, (prescriptionRequired ? "YES" : "NO"));
    }
}

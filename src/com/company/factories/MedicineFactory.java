package com.company.factories;

import com.company.models.Medicine;
public class MedicineFactory {
    public static Medicine createMedicine(String name, double price, String manufacturer, int quantity, boolean isPrescription, int categoryId) {

        return new Medicine(name, price, manufacturer, quantity, isPrescription, categoryId);
    }
}
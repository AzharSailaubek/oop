package com.company.controllers;



import com.company.controllers.interfaces.IPharmacyController;

import com.company.models.Medicine;

import com.company.models.Sale;

import com.company.repositories.interfaces.IMedicineRepository;

import com.company.repositories.interfaces.ISaleRepository;

import com.company.factories.MedicineFactory; // Импорт нашей фабрики



import java.util.List;



public class PharmacyController implements IPharmacyController {



    private final IMedicineRepository medicineRepo;

    private final ISaleRepository saleRepo;



    public PharmacyController(IMedicineRepository medicineRepo, ISaleRepository saleRepo) {

        this.medicineRepo = medicineRepo;

        this.saleRepo = saleRepo;

    }



    @Override

    public String addMedicine(String name, double price, String manufacturer, int quantity, boolean isPrescription, int userRole) {



        if (userRole != 1) {

            return "Access Denied: Only admins can add new medicines.";

        }



        try {

            if (name == null || name.trim().isEmpty()) return "Error: Name cannot be empty!";

            if (price <= 0) return "Error: Price must be positive!";

            if (quantity < 0) return "Error: Quantity cannot be negative!";



            Medicine medicine = MedicineFactory.createMedicine(name, price, manufacturer, quantity, isPrescription, 1);



            boolean created = medicineRepo.createMedicine(medicine);

            return created ? "Medicine '" + name + "' added successfully!" : "Failed to add medicine!";

        } catch (Exception e) {

            return "Error: " + e.getMessage();

        }

    }



    @Override

    public String showAllMedicines() {

        List<Medicine> list = medicineRepo.getAllMedicines();



        if (list.isEmpty()) return "The pharmacy inventory is empty.";



        StringBuilder sb = new StringBuilder("--- PHARMACY INVENTORY ---\n");

        for (Medicine m : list) {

            sb.append(m.toString()).append("\n");

        }

        return sb.toString();

    }



    @Override

    public String sellMedicine(String medicineName, int quantity, boolean hasPrescription) {

        if (quantity <= 0) return "Error: Selling quantity must be greater than zero!";



        Medicine medicine = medicineRepo.getMedicineByName(medicineName);

        if (medicine == null) return "Error: Medicine '" + medicineName + "' not found!";



        if (medicine.isPrescriptionRequired() && !hasPrescription)

            return "Transaction Denied: This medicine requires a prescription!";



        if (medicine.getQuantity() < quantity)

            return "Error: Not enough stock. Available: " + medicine.getQuantity();



        boolean updated = medicineRepo.updateQuantity(medicine.getId(), medicine.getQuantity() - quantity);



        if (updated) {

            double total = medicine.getPrice() * quantity;

            saleRepo.createSale(new Sale(medicine.getId(), quantity, total));

            return "Success! Sold " + quantity + " units of " + medicine.getName() + ". Total: " + total;

        }



        return "Error: Transaction failed during database update.";

    }



    @Override

    public String getLowStockMedicines() {

        List<Medicine> allMed = medicineRepo.getAllMedicines();



        List<Medicine> lowStock = allMed.stream()

                .filter(m -> m.getQuantity() < 5)

                .toList();



        if (lowStock.isEmpty()) {

            return "All medicines are well-stocked (minimum 5 units each).";

        }



        StringBuilder sb = new StringBuilder("--- LOW STOCK ALERT ---\n");



        lowStock.forEach(m -> sb.append(String.format("(!) %-15s | Left: %d\n", m.getName(), m.getQuantity())));



        return sb.toString();

    }



    @Override

    public String getSalesHistory() {



        List<String> history = saleRepo.getDetailedSalesHistory();



        if (history.isEmpty()) return "No sales have been recorded yet.";



        StringBuilder sb = new StringBuilder("--- SALES HISTORY (JOIN REPORT) ---\n");

        for (String record : history) {

            sb.append(record).append("\n");

        }

        return sb.toString();

    }


    @Override
    public String updatePrice(int id, double price) {boolean updated = medicineRepo.updatePrice(id, price);

        if (updated) return "Success: Price updated!";

        else return "Error: Could not update price.";
    }

    public String removeMedicine(int id, int userRole) {
        if (userRole != 1) return "Access Denied!";
        boolean deleted = medicineRepo.deleteMedicine(id);
        return deleted ? "Medicine moved to archive (deleted from inventory)!" : "Failed to delete.";
    }

    @Override
    public String getRevenueReport() {
        double total = saleRepo.getTotalRevenue();
        return String.format("--- FINANCIAL REPORT ---\nTotal Revenue: %.2f KZT", total);
    }
}


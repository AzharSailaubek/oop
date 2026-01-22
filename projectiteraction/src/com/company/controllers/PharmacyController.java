package com.company.controllers;

import com.company.models.Medicine;
import com.company.models.Sale;
import com.company.repositories.interfaces.IMedicineRepository;
import com.company.repositories.interfaces.ISaleRepository;

import java.util.List;

public class PharmacyController {

    private final IMedicineRepository medicineRepo;
    private final ISaleRepository saleRepo;

    public PharmacyController(IMedicineRepository medicineRepo, ISaleRepository saleRepo) {
        this.medicineRepo = medicineRepo;
        this.saleRepo = saleRepo;
    }

    @Override
    public String addMedicine(String name, double price, String manufacturer, int quantity, boolean is Prescription){

        Medicine medicine = new Medicine(name, price, manufacturer, quantity, isPrescription);
        boolean created = medicineRepo.createMedicine(medicine);

        return created ? "Medicine added successfully!" : "Failed to add medicine!";
    }

    public String showAllMedicines() {
        List<Medicine> list = medicineRepo.getAllMedicines();

        if (list.isEmpty()) return "No medicines found.";

        StringBuilder sb = new StringBuilder();
        for (Medicine m : list) {
            sb.append(m).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String sellMedicine(String medicineName, int quantity, boolean hasPrescription) {
        Medicine medicine = medicineRepo.getMedicineByName(medicineName);
        if (medicine == null) return "Medicine not found!";

        if (medicine.isPrescriptionRequired() && !hasPrescription)
            return "This medicine requires a prescription!";

        if (medicine.getQuantity() < quantity)
            return "Not enough medicine in stock!";

        medicineRepo.updateQuantity(medicine.getId(), medicine.getQuantity() - quantity);

        saleRepo.createSale(new Sale(medicine.getId(), quantity));

        double total = medicine.getPrice() * quantity;
        return "Sold " + medicine.getName() + " | Total price: " + total;
    }
}

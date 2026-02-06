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
        // 1. Role Management (Защита на уровне контроллера)
        if (userRole != 1) { // Допустим, 1 - это Admin
            return "Access Denied: Only admins can add new medicines.";
        }

        try {
            // 2. Data Validation (Валидация данных)
            if (name == null || name.trim().isEmpty()) return "Error: Name cannot be empty!";
            if (price <= 0) return "Error: Price must be positive!";
            if (quantity < 0) return "Error: Quantity cannot be negative!";

            // 3. Application of Design Patterns (Factory)
            // Мы не пишем new Medicine(...), а используем фабрику
            Medicine medicine = MedicineFactory.createMedicine(name, price, manufacturer, quantity, isPrescription, 1);

            boolean created = medicineRepo.createMedicine(medicine);
            return created ? "Medicine '" + name + "' added successfully!" : "Failed to add medicine!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @Override
    public String showAllMedicines() {
        // Здесь используется JOIN внутри репозитория (мы это уже настроили)
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
        // Валидация ввода
        if (quantity <= 0) return "Error: Selling quantity must be greater than zero!";

        Medicine medicine = medicineRepo.getMedicineByName(medicineName);
        if (medicine == null) return "Error: Medicine '" + medicineName + "' not found!";

        // Бизнес-логика: проверка рецепта
        if (medicine.isPrescriptionRequired() && !hasPrescription)
            return "Transaction Denied: This medicine requires a prescription!";

        // Проверка наличия
        if (medicine.getQuantity() < quantity)
            return "Error: Not enough stock. Available: " + medicine.getQuantity();

        // Обновление данных
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

        // Application of Lambda expressions
        // Фильтруем список: оставляем только те, где остаток меньше 5
        List<Medicine> lowStock = allMed.stream()
                .filter(m -> m.getQuantity() < 5)
                .toList();

        if (lowStock.isEmpty()) {
            return "All medicines are well-stocked (minimum 5 units each).";
        }

        StringBuilder sb = new StringBuilder("--- LOW STOCK ALERT ---\n");
        // Еще одна лямбда для вывода названий
        lowStock.forEach(m -> sb.append(String.format("(!) %-15s | Left: %d\n", m.getName(), m.getQuantity())));

        return sb.toString();
    }

    @Override
    public String getSalesHistory() {
        // Здесь используется JOIN (Sales + Medicines) внутри репозитория
        List<String> history = saleRepo.getDetailedSalesHistory();

        if (history.isEmpty()) return "No sales have been recorded yet.";

        StringBuilder sb = new StringBuilder("--- SALES HISTORY (JOIN REPORT) ---\n");
        for (String record : history) {
            sb.append(record).append("\n");
        }
        return sb.toString();
    }
}
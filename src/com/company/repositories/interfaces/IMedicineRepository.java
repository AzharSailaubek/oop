package com.company.repositories.interfaces;

import com.company.models.Medicine;
import java.util.List;

public interface IMedicineRepository {
    boolean createMedicine(Medicine m);

    Medicine getMedicine(int id);

    Medicine getMedicineByName(String name);

    List<Medicine> getAllMedicines();

    boolean updateQuantity(int id, int quantity);
}
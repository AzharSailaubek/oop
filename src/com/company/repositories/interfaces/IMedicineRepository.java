package com.company.repositories.interfaces;



import com.company.models.Medicine;

import com.company.models.Category;

import java.util.List;



public interface IMedicineRepository {

    boolean createMedicine(Medicine m);



    Medicine getMedicine(int id);



    Medicine getMedicineByName(String name);



    List<Medicine> getAllMedicines();



    List<Category> getAllCategories();



    boolean updateQuantity(int id, int quantity);



    boolean updatePrice(int id, double price);

    boolean deleteMedicine(int id);

}
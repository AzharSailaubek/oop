package com.company.controllers.interfaces;



public interface IPharmacyController {



    String addMedicine(String name, double price, String manufacturer,

                       int quantity, boolean prescriptionRequired, int userRole);



    String showAllMedicines();



    String sellMedicine(String medicineName, int quantity, boolean hasPrescription);



    String getSalesHistory();



    String getLowStockMedicines();



}
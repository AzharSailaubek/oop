package com.company;

import com.company.controllers.PharmacyController;
import com.company.controllers.interfaces.IPharmacyController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.MedicineRepository;
import com.company.repositories.SaleRepository;
import com.company.repositories.interfaces.IMedicineRepository;
import com.company.repositories.interfaces.ISaleRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = new PostgresDB(
                "jdbc:postgresql://localhost:5432",
                "postgres",
                "0000",
                "pharmacy"
        );

        MedicineRepository medRepo = new MedicineRepository(db);
        SaleRepository saleRepo = new SaleRepository(db);
        com.company.controllers.PharmacyController controller = new com.company.controllers.PharmacyController(medRepo, saleRepo);

        com.company.MyApplication app = new com.company.MyApplication(controller);
        app.start();

        db.close();
    }
}


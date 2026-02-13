package com.company;

import com.company.controllers.PharmacyController;
import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.MedicineRepository;
import com.company.repositories.SaleRepository;
import com.company.repositories.UserRepository;

public class Main {
    public static void main(String[] args) {
        IDB db = PostgresDB.getInstance(
                System.getenv("DB_URL"),
                System.getenv("DB_USER"),
                System.getenv("DB_PASSWORD"),
                System.getenv("DB_NAME")
        );

        MedicineRepository medRepo = new MedicineRepository(db);
        SaleRepository saleRepo = new SaleRepository(db);
        UserRepository userRepo = new UserRepository(db);

        PharmacyController controller = new PharmacyController(medRepo, saleRepo);

        MyApplication app = new MyApplication(controller, userRepo);

        app.start();
        db.close();
    }
}
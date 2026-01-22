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
                "jdbc:postgresql://localhost:5433",
                "postgres",
                "0000",
                "pharmacy"
        );

        IUserRepository repo = new UserRepository(db);
        IUserController controller = new UserController(repo);

        MyApplication app = new MyApplication(controller);
        app.start();

        db.close();
    }
}

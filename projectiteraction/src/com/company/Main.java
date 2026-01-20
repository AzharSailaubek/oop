package com.company;

import com.company.data.PostgresDB;
import com.company.data.interfaces.IDB;
import com.company.repositories.MedicineRepository;
import com.company.repositories.SaleRepository;

public class Main {

    public static void main(String[] args) {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");

        String password = System.getenv("DB_PASSWORD");
        String dbName = System.getenv("DB_NAME");

        IDB db = new PostgresDB(url, user, password, dbName);
        IUserRepository repo = new UserRepository(db);
        IUserController controller = new UserController(repo);

        MyApplication app = new MyApplication(controller);

        app.start();

        db.close();
    }
}

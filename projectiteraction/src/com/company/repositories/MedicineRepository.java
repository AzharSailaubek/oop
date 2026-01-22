package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Medicine;
import com.company.repositories.interfaces.IMedicineRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepository implements IMedicineRepository {
    private final IDB db;

    public MedicineRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createMedicine(Medicine m) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO medicines(name, price, manufacturer, quantity, prescription_required) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, m.getName());
            st.setDouble(2, m.getPrice());
            st.setString(3, m.getManufacturer());
            st.setInt(4, m.getQuantity());
            st.setBoolean(5, m.isPrescriptionRequired());
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return false;
        }
    }

package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Sale;
import com.company.repositories.interfaces.ISaleRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SaleRepository implements ISaleRepository {
    private final IDB db;

    public SaleRepository(IDB db) {
        this.db = db;
    }

    @Override
    public boolean createSale(Sale s) {
        try (Connection con = db.getConnection()) {
            String sql = "INSERT INTO sales(medicine_id, quantity, total_price, sale_date) VALUES (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, s.getMedicineId());
            st.setInt(2, s.getQuantity());
            st.setDouble(3, s.getTotalPrice());
            st.setDate(4, java.sql.Date.valueOf(s.getSaleDate()));
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return false;
        }
    }
}
package com.company.repositories;

import com.company.data.interfaces.IDB;
import com.company.models.Sale;
import com.company.repositories.interfaces.ISaleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<String> getDetailedSalesHistory() {
        List<String> history = new ArrayList<>();
        String sql = "SELECT s.id, m.name, s.quantity, s.total_price " +
                "FROM sales s " +
                "JOIN medicines m ON s.medicine_id = m.id";

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String record = "Sale #" + rs.getInt("id") +
                        " | Medicine: " + rs.getString("name") +
                        " | Qty: " + rs.getInt("quantity") +
                        " | Total price: " + rs.getDouble("total_price");
                history.add(record);
            }
        } catch (SQLException e) {
            System.out.println("JOIN Error: " + e.getMessage());
        }
        return history;
    }
}
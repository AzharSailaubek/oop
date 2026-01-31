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
            String sql = "INSERT INTO medicines(name, price, manufacturer, quantity, prescription_required, category) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, m.getName());
            st.setDouble(2, m.getPrice());
            st.setString(3, m.getManufacturer());
            st.setInt(4, m.getQuantity());
            st.setBoolean(5, m.isPrescriptionRequired());
            st.setString(6, m.getCategory());
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Medicine getMedicine(int id) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id, name, price, manufacturer, quantity, prescription_required, category FROM medicines WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("manufacturer"),
                        rs.getInt("quantity"),
                        rs.getBoolean("prescription_required"), // ДОБАВЛЕНА ЗАПЯТАЯ
                        rs.getString("category")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Medicine getMedicineByName(String name) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id, name, price, manufacturer, quantity, prescription_required, category FROM medicines WHERE name=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("manufacturer"),
                        rs.getInt("quantity"),
                        rs.getBoolean("prescription_required"), // ДОБАВЛЕНА ЗАПЯТАЯ
                        rs.getString("category")
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>();
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id, name, price, manufacturer, quantity, prescription_required, category FROM medicines";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(new Medicine(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("manufacturer"),
                        rs.getInt("quantity"),
                        rs.getBoolean("prescription_required"), // ДОБАВЛЕНА ЗАПЯТАЯ
                        rs.getString("category")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean updateQuantity(int id, int quantity) {
        try (Connection con = db.getConnection()) {
            String sql = "UPDATE medicines SET quantity=? WHERE id=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, quantity);
            st.setInt(2, id);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return false;
        }
    }
}
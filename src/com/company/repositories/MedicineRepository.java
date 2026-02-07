package com.company.repositories;
import com.company.data.interfaces.IDB;

import com.company.models.Category;

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
            String sql = "INSERT INTO medicines(name, price, manufacturer, quantity, prescription_required, category_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, m.getName());
            st.setDouble(2, m.getPrice());
            st.setString(3, m.getManufacturer());
            st.setInt(4, m.getQuantity());
            st.setBoolean(5, m.isPrescriptionRequired());
            st.setInt(6, m.getCategoryId());
            st.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("SQL error (createMedicine): " + e.getMessage());
            return false;
        }
    }

    @Override
    public Medicine getMedicine(int id) {
        try (Connection con = db.getConnection()) {

            String sql = "SELECT m.*, c.name AS category_name FROM medicines m " +
                    "JOIN categories c ON m.category_id::int = c.id " + "WHERE m.id = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToMedicine(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getMedicine): " + e.getMessage());
        }
        return null;
    }

    @Override
    public Medicine getMedicineByName(String name) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT m.*, c.name AS category_name FROM medicines m " +
                    "JOIN categories c ON m.category_id::int = c.id " +
                    "WHERE m.name = ? AND m.is_active = true";

            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return mapResultSetToMedicine(rs);
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getMedicineByName): " + e.getMessage());
        }
        return null;
    }

    @Override

    public List<Medicine> getAllMedicines() {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT m.*, c.name AS category_name FROM medicines m " +
                "JOIN categories c ON m.category_id::int = c.id " +
                "WHERE m.is_active = true";

        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(mapResultSetToMedicine(rs));
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getAllMedicines): " + e.getMessage());
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
            System.out.println("SQL error (updateQuantity): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePrice(int id, double price) {
        String sql = "UPDATE medicines SET price=? WHERE id=?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {st.setDouble(1, price);
            st.setInt(2, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM categories";
        try (Connection con = db.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            System.out.println("SQL error (getAllCategories): " + e.getMessage());
        }
        return categories;
    }
    private Medicine mapResultSetToMedicine(ResultSet rs) throws SQLException {
        Medicine med = new Medicine(

                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getString("manufacturer"),
                rs.getInt("quantity"),
                rs.getBoolean("prescription_required"),
                rs.getInt("category_id")
        );

        med.setCategoryName(rs.getString("category_name"));
        return med;
    }

    @Override
    public boolean deleteMedicine(int id) {
        String sql = "UPDATE medicines SET is_active = false WHERE id = ?";
        try (Connection con = db.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            return false;
        }
    }
}
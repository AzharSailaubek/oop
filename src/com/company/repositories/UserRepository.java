package com.company.repositories;
import com.company.data.interfaces.IDB;
import com.company.models.User;
import com.company.repositories.interfaces.IUserRepository;
import java.sql.*;

public class UserRepository implements IUserRepository {
    private final IDB db;
    public UserRepository(IDB db) { this.db = db; }

    @Override
    public User authenticate(String username, String password) {
        try (Connection con = db.getConnection()) {
            String sql = "SELECT id, username, role_id FROM users WHERE username = ? AND password = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getInt("role_id"));
            }
        } catch (SQLException e) {
            System.out.println("Auth error: " + e.getMessage());
        }
        return null;
    }
}
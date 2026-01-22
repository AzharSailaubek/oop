package com.company.data;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {

    private final String host;
    private final String username;
    private final String password;
    private final String dbName;

    private Connection connection;

    public PostgresDB(String host, String username, String password, String dbName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            Class.forName("org.postgresql.Driver");

            String url = host + "/" + dbName;
            connection = DriverManager.getConnection(url, username, password);

            return connection;
        } catch (Exception e) {
            System.out.println("Postgres connection error: " + e.getMessage());
            return null;
        }
    }
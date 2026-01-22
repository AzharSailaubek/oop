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
            String sql = "INSERT INTO sales(medicine_id, quantity, sale_date) VALUES (?, ?, ?)";
            PreparedStatement st = con.prepareStatement(sql);

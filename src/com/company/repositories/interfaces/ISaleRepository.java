package com.company.repositories.interfaces;

import com.company.models.Sale;
import java.util.List;
public interface ISaleRepository {
    boolean createSale(Sale s);
    List<String> getDetailedSalesHistory();
    double getTotalRevenue();
}
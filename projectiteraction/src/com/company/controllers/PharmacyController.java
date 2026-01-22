package com.company.controllers;

import com.company.models.Medicine;
import com.company.models.Sale;
import com.company.repositories.interfaces.IMedicineRepository;
import com.company.repositories.interfaces.ISaleRepository;

import java.util.List;

public class PharmacyController {

    private final IMedicineRepository medicineRepo;
    private final ISaleRepository saleRepo;

    public PharmacyController(IMedicineRepository medicineRepo, ISaleRepository saleRepo) {
        this.medicineRepo = medicineRepo;
        this.saleRepo = saleRepo;
    }
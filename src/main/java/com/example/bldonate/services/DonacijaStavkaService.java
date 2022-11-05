package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.DonacijaStavka;
import com.example.bldonate.models.requests.DonacijaStavkaRequest;

import java.math.BigDecimal;
import java.util.List;

public interface DonacijaStavkaService {

    List<DonacijaStavka> getAll();

    DonacijaStavka findById(Integer id) throws NotFoundException;

    DonacijaStavka insert(DonacijaStavkaRequest request) throws NotFoundException;

    DonacijaStavka update(Integer id,DonacijaStavkaRequest request) throws NotFoundException;

    void delete(Integer id) throws Exception;

}

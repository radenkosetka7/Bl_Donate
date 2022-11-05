package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Donacija;
import com.example.bldonate.models.dto.RezervacijaStavka;
import com.example.bldonate.models.requests.DonacijaRequest;
import com.example.bldonate.models.requests.RezervacijaStavkaRequest;

import java.math.BigDecimal;
import java.util.List;

public interface RezervacijaStavkaService {

 List<RezervacijaStavka> getAll();

    RezervacijaStavka findById(Integer id) throws NotFoundException;

    RezervacijaStavka  insert(RezervacijaStavkaRequest request) throws NotFoundException;

    RezervacijaStavka update(Integer id,RezervacijaStavkaRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;


}

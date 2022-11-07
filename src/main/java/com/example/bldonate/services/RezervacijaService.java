package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Rezervacija;
import com.example.bldonate.models.requests.RezervacijaRequest;

import java.util.Date;
import java.util.List;

public interface RezervacijaService {

   List<Rezervacija> getAll();

    Rezervacija findById(Integer id) throws NotFoundException;

    Rezervacija  insert(RezervacijaRequest request) throws NotFoundException;

    Rezervacija update(Integer id,RezervacijaRequest request) throws NotFoundException;

    List<Rezervacija> getAllReservations(Integer id);
    void delete(Integer id) throws NotFoundException;
    List<Rezervacija> getAllArchiveReservations(Integer id);
    List<Rezervacija> getAllArchiveRange(Integer id,Date pocetniDatum, Date krajnjiDatum);
}

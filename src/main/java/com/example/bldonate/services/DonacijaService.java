package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Donacija;
import com.example.bldonate.models.requests.DonacijaRequest;

import java.util.Date;
import java.util.List;

//
//@Service
//@RequiredArgsConstructor
//public  class Donacija

public interface DonacijaService {

    List<Donacija> getAll(Integer id);

    Donacija findById(Integer id) throws NotFoundException;

    Donacija insert(DonacijaRequest request) throws NotFoundException;

    Donacija update(Integer id,DonacijaRequest request) throws NotFoundException;

    void delete(Integer id) throws Exception;

    List<Donacija> getAllDonacijaByDonorId(Integer id);

    List<Donacija> getAllArchiveDonations(Integer id);
    List<Donacija> getAllArchiveRange(Integer id, Date pocetniDatum, Date krajnjiDatum);

    List<Donacija> get();
}

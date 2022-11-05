package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.FizickoLice;
import com.example.bldonate.models.dto.Korisnik;
import com.example.bldonate.models.dto.PravnoLice;
import com.example.bldonate.models.dto.Rezervacija;
import com.example.bldonate.models.requests.FizickoLiceRequest;
import com.example.bldonate.models.requests.PravnoLiceRequest;

import java.util.List;

public interface PravnoLiceService {

    List<PravnoLice> getAll();

    PravnoLice findById(Integer id) throws NotFoundException;

    PravnoLice insert(PravnoLiceRequest request) throws NotFoundException;

    void delete(Integer id) throws Exception;

    List<PravnoLice> getAllUnapprovedUsers();

    PravnoLice update(Integer id,PravnoLiceRequest request) throws NotFoundException;

    List<Rezervacija> getAllReservations(Integer id);
}

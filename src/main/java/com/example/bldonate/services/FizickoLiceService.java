package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.*;
import com.example.bldonate.models.requests.DonatorRequest;
import com.example.bldonate.models.requests.FizickoLiceRequest;

import java.util.List;

public interface FizickoLiceService {

   List<FizickoLice> getAll();

    FizickoLice findById(Integer id) throws NotFoundException;

    FizickoLice  insert(FizickoLiceRequest request) throws NotFoundException;

    FizickoLice update(Integer id,FizickoLiceRequest request) throws NotFoundException;

    void delete(Integer id) throws Exception;

    List<FizickoLice> getAllUnapprovedUsers();

  List<Rezervacija> getAllReservations(Integer id);

}

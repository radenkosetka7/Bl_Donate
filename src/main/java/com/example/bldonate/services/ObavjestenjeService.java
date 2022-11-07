package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Obavjestenje;
import com.example.bldonate.models.requests.ObavjestenjeRequest;

import java.util.List;

public interface ObavjestenjeService {

  List<Obavjestenje> getAll();

    Obavjestenje findById(Integer id) throws NotFoundException;

    Obavjestenje  insert(ObavjestenjeRequest request) throws NotFoundException;

    Obavjestenje update(Integer id,ObavjestenjeRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;

    List<Obavjestenje> getAllObavjestenjaKorisnik(Integer id);


}

package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Korisnik;
import com.example.bldonate.models.dto.Obavjestenje;
import com.example.bldonate.models.dto.Oglas;
import com.example.bldonate.models.requests.KorisnikRequest;
import com.example.bldonate.models.requests.OglasRequest;

import java.util.List;

public interface KorisnikService {
    List<Korisnik> getAll();

    Korisnik findById(Integer id) throws NotFoundException;

    Korisnik  insert(KorisnikRequest request) throws NotFoundException;

   Korisnik update(Integer id,KorisnikRequest request ) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;

    List<Korisnik> getAllUnapprovedUsers();
}


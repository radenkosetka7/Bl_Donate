package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Proizvod;
import com.example.bldonate.models.requests.ProizvodRequest;

import java.util.Date;
import java.util.List;

public interface ProizvodService {

   List<Proizvod> getAll();

    Proizvod findById(Integer id) throws NotFoundException;

    Proizvod insert(ProizvodRequest request) throws NotFoundException;

    Proizvod update(Integer id, ProizvodRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;

}
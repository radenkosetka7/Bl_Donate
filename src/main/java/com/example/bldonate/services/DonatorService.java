package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Donacija;
import com.example.bldonate.models.dto.Donator;
import com.example.bldonate.models.entities.DonatorEntity;
import com.example.bldonate.models.requests.DonacijaRequest;
import com.example.bldonate.models.requests.DonatorRequest;

import java.util.List;

public interface DonatorService {

//    List<Donator> getAll();
//
   Donator findById(Integer id) throws NotFoundException;

    DonatorEntity insert(DonatorRequest request) throws NotFoundException;

   DonatorEntity update(Integer id,DonatorRequest request) throws NotFoundException;
   void delete(Integer id) throws NotFoundException;
}

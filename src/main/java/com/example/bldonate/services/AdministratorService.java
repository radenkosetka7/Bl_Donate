package com.example.bldonate.services;


import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Administrator;
import com.example.bldonate.models.requests.AdministratorRequest;

import java.security.NoSuchAlgorithmException;

public interface AdministratorService {


    Administrator findById(Integer id) throws NotFoundException;

    Administrator insert(AdministratorRequest request) throws NotFoundException, NoSuchAlgorithmException;

    void deleteUser(Integer id) throws Exception;

    void deleteDonor(Integer id) throws Exception;

}

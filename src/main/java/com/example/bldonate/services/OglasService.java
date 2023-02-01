package com.example.bldonate.services;



import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Oglas;
import com.example.bldonate.models.requests.OglasRequest;

import java.util.List;

public interface OglasService {

    List<Oglas> getAll();

    Oglas findById(Integer id) throws NotFoundException;

    Oglas  insert(OglasRequest request) throws NotFoundException;

    Oglas update(Integer id,OglasRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;

    List<Oglas> getAllOglasiById(Integer id);

}

package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.JedinicaMjere;
import com.example.bldonate.models.entities.JedinicaMjereEntity;

import java.util.List;

public interface JedinicaMjereService {

    JedinicaMjereEntity findById(Integer id) throws NotFoundException;
    List<JedinicaMjereEntity> getAll();

}

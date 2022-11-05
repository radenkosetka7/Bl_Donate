package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.entities.JedinicaMjereEntity;
import com.example.bldonate.repositories.JedinicaMjereRepository;
import com.example.bldonate.services.JedinicaMjereService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Service
@Transactional
public class JedinicaMjereImpl implements JedinicaMjereService {

    private final JedinicaMjereRepository repository;
    private final ModelMapper mapper;

    @PersistenceContext
    private EntityManager manager;


    public JedinicaMjereImpl(JedinicaMjereRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public JedinicaMjereEntity findById(Integer id) throws NotFoundException {
        return repository.findById(id).get();

    }
}

package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.JedinicaMjere;
import com.example.bldonate.models.entities.JedinicaMjereEntity;
import com.example.bldonate.repositories.JedinicaMjereRepository;
import com.example.bldonate.services.JedinicaMjereService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<JedinicaMjere> getAll() {
        return repository.findAll().stream().map(e->mapper.map(e, JedinicaMjere.class)).collect(Collectors.toList());
    }
}

package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.entities.KategorijaProizvodaEntity;
import com.example.bldonate.repositories.KategorijaProizvodarRepository;
import com.example.bldonate.services.KategorijaProizvodaService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KategorijaProizvodaImpl implements KategorijaProizvodaService {


    private final KategorijaProizvodarRepository repository;
    private final ModelMapper mapper;

    @PersistenceContext
    private EntityManager manager;


    public KategorijaProizvodaImpl(KategorijaProizvodarRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    @Override
    public KategorijaProizvodaEntity findById(Integer id) throws NotFoundException {
        return repository.findById(id).get();

    }

    @Override
    public List<KategorijaProizvodaEntity> getAll() {
        return repository.findAll();
    }
}

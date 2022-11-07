package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Obavjestenje;
import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.entities.ObavjestenjeEntity;
import com.example.bldonate.models.requests.ObavjestenjeRequest;
import com.example.bldonate.repositories.KorisnikRepository;
import com.example.bldonate.repositories.ObavjestenjeRepository;
import com.example.bldonate.services.ObavjestenjeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ObavjestenjeImpl implements ObavjestenjeService {

   private final ObavjestenjeRepository repository;
    private final ModelMapper mapper;

    private final KorisnikRepository korisnikRepository;

    @PersistenceContext
    private EntityManager manager;

    public ObavjestenjeImpl(ObavjestenjeRepository repository, ModelMapper mapper, KorisnikRepository korisnikRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.korisnikRepository = korisnikRepository;
    }


    @Override
    public List<Obavjestenje> getAll() {
        return repository.findAll().stream().map(e -> mapper.map(e, Obavjestenje.class)).collect(Collectors.toList());
    }

    @Override
    public Obavjestenje findById(Integer id) throws NotFoundException {
        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new), Obavjestenje.class);
    }

    @Override
    public Obavjestenje insert(ObavjestenjeRequest request) throws NotFoundException {
        ObavjestenjeEntity entity = mapper.map(request, ObavjestenjeEntity.class);
        KorisnikEntity korisnikEntity=korisnikRepository.findById(request.getKorisnik()).get();
        entity.setId(null);
        entity.setKorisnik(korisnikEntity);
        entity.setSadrzaj(request.getSadrzaj());
        entity = repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());
    }

    @Override
    public Obavjestenje update(Integer id,ObavjestenjeRequest request) throws NotFoundException {
        if(!repository.existsById(id))
        {
            throw new NotFoundException();
        }
        ObavjestenjeEntity entity = repository.findById(id).get();
        if (request.getProcitano() != null && !request.getProcitano().equals(entity.getProcitano())) {

            entity.setProcitano(request.getProcitano());
        }
        entity = repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        }
        else
        {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Obavjestenje> getAllObavjestenjaKorisnik(Integer id) {
        return repository.getAllObavjestenjaKorisnik(id).stream().map(e->mapper.map(e,Obavjestenje.class)).collect(Collectors.toList());
    }

}

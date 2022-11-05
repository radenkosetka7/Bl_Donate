package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Donator;
import com.example.bldonate.models.entities.DonatorEntity;
import com.example.bldonate.models.requests.DonatorRequest;
import com.example.bldonate.repositories.DonatorRepository;
import com.example.bldonate.services.DonatorService;
import com.example.bldonate.util.SecureUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DonatorImpl implements DonatorService {

   private final DonatorRepository repository;
    private final ModelMapper mapper;

    @PersistenceContext
    private EntityManager manager;

    public DonatorImpl(DonatorRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public DonatorEntity insert(@RequestBody DonatorRequest request) throws NotFoundException {
        DonatorEntity entity=mapper.map(request,DonatorEntity.class);
        entity.setId(null);
        byte[] salt=entity.getLozinka().getBytes(StandardCharsets.UTF_8);
        String hash= SecureUtils.getSecurePassword(request.getLozinka(),salt);
        entity.setLozinka(hash);
        entity=repository.saveAndFlush(entity);
        manager.refresh(entity);
        return entity;
    }


    @Override
    public Donator findById(Integer id) throws NotFoundException {
        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new),Donator.class);
    }
    /*
    @Override
    public List<Donator> getAll() {
        return repository.findAll().stream().map(e->mapper.map(e,Donator.class)).collect(Collectors.toList());
    }

     */

    @Override
    public DonatorEntity update(Integer id, DonatorRequest request) throws NotFoundException {
        if(!repository.existsById(id))
        {
            throw new NotFoundException();
        }

        DonatorEntity entity=repository.findById(id).get();
        if(request.getOdoboren()!=null && !request.getOdoboren().equals(entity.getOdoboren()))
        {
            entity.setOdoboren(request.getOdoboren());
        }
        if(request.getAdresa()!=null && request.getAdresa().length()>0 && !request.getAdresa().equals(entity.getAdresa()))
        {
            entity.setAdresa(request.getAdresa());
        }
        if(request.getIme()!=null && request.getIme().length()>0 && !request.getIme().equals(entity.getIme()))
        {
            entity.setIme(request.getIme());
        }
        if(request.getEmail()!=null && request.getEmail().length()>0 && !request.getEmail().equals(entity.getEmail()))
        {
            entity.setEmail(request.getEmail());
        }
        if(request.getBrojTelefona()!=null && request.getBrojTelefona().length()>0 && !request.getBrojTelefona().equals(entity.getBrojTelefona()))
        {
            entity.setBrojTelefona(request.getBrojTelefona());
        }
        if(request.getKorisnickoIme()!=null && request.getKorisnickoIme().length()>0 && !request.getKorisnickoIme().equals(entity.getKorisnickoIme()))
        {
            entity.setKorisnickoIme(request.getKorisnickoIme());
        }
        if(request.getLozinka()!=null && request.getLozinka().length()>0)
        {
            byte[] salt=request.getLozinka().getBytes(StandardCharsets.UTF_8);
            String otisak=SecureUtils.getSecurePassword(request.getLozinka(),salt);
            if(!otisak.equals(entity.getLozinka()))
            {
                entity.setLozinka(otisak);
            }
        }
        entity=repository.saveAndFlush(entity);
        manager.refresh(entity);
        return entity;
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
}

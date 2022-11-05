package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Administrator;
import com.example.bldonate.models.dto.FizickoLice;
import com.example.bldonate.models.dto.Korisnik;
import com.example.bldonate.models.dto.PravnoLice;
import com.example.bldonate.models.entities.AdministratorEntity;
import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.requests.AdministratorRequest;
import com.example.bldonate.repositories.AdministratorRepository;
import com.example.bldonate.services.AdministratorService;
import com.example.bldonate.services.FizickoLiceService;
import com.example.bldonate.services.KorisnikService;
import com.example.bldonate.services.PravnoLiceService;
import com.example.bldonate.util.SecureUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

@Service
@Transactional
public class AdministratorImpl implements AdministratorService {


    private final ModelMapper mapper;
    private final AdministratorRepository repository;

    private final FizickoLiceService fizickoLiceService;
    private final PravnoLiceService pravnoLiceService;
    private final KorisnikService korisnikService;

    @PersistenceContext
    private EntityManager manager;

    public AdministratorImpl(AdministratorRepository repository, ModelMapper mapper, FizickoLiceService fizickoLiceService, PravnoLiceService pravnoLiceService, KorisnikService korisnikService) {
        this.mapper = mapper;
        this.repository = repository;
        this.fizickoLiceService = fizickoLiceService;
        this.pravnoLiceService = pravnoLiceService;
        this.korisnikService = korisnikService;
    }

    @Override
    public Administrator findById(Integer id) throws NotFoundException {
        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new), Administrator.class);

    }

    @Override
    public Administrator insert(AdministratorRequest request) throws NotFoundException, NoSuchAlgorithmException {
        AdministratorEntity entity=mapper.map(request,AdministratorEntity.class);
        entity.setId(null);
        byte[] salt=entity.getLozinka().getBytes(StandardCharsets.UTF_8);
        String hash=SecureUtils.getSecurePassword(request.getLozinka(),salt);
        entity.setLozinka(hash);
        entity = repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());

    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        Korisnik korisnik=korisnikService.findById(id);

        if(korisnik!=null)
        {
            korisnikService.delete(id);
            //TODO: posalji mail
        }

    }

    @Override
    public void deleteDonor(Integer id) throws Exception {

        FizickoLice fizickoLice=fizickoLiceService.findById(id);
        PravnoLice pravnoLice=pravnoLiceService.findById(id);
        if(fizickoLice!=null)
        {
            fizickoLiceService.delete(id);
            //TODO: posalji mail
        }
        else if(pravnoLice!=null)
        {
            pravnoLiceService.delete(id);
            //TODO: posalji mail
        }

    }
    /*
    @Override
    public Administrator findById(Integer id) throws NotFoundException {
        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new),Administrator.class);
    }*/
}

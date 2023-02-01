package com.example.bldonate.services.impl;


import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Oglas;
import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.entities.OglasEntity;
import com.example.bldonate.models.requests.OglasRequest;
import com.example.bldonate.repositories.KorisnikRepository;
import com.example.bldonate.repositories.OglasRepository;
import com.example.bldonate.services.OglasService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OglasImpl implements OglasService {

    private final OglasRepository repository;
    private final ModelMapper mapper;
    private final KorisnikRepository korisnikRepository;

    TypeMap<OglasEntity,Oglas> property;

    @PersistenceContext
    private EntityManager manager;

    public OglasImpl(OglasRepository repository, ModelMapper mapper, KorisnikRepository korisnikRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.korisnikRepository = korisnikRepository;
        this.property =this.mapper.createTypeMap(OglasEntity.class,Oglas.class);

        property.addMappings(
                m -> m.map(src->src.getKorisnik(), Oglas::setKorisnik)
        );
    }



    @Override
    public List<Oglas> getAll() {
        return repository.findAll().stream().map(e->mapper.map(e,Oglas.class)).collect(Collectors.toList());
    }

    @Override
    public Oglas findById(Integer id) throws NotFoundException {
        return  mapper.map(repository.findById(id).orElseThrow(NotFoundException::new),Oglas.class);
    }

    @Override
    public Oglas insert(OglasRequest request) throws NotFoundException {
        OglasEntity entity=mapper.map(request,OglasEntity.class);
        KorisnikEntity korisnikEntity=korisnikRepository.findById(request.getKorisnikId()).get();

        entity.setId(null);

        entity.setKorisnik(korisnikEntity);
        entity=repository.saveAndFlush(entity);

        manager.refresh(entity);
        return findById(entity.getId());
    }

    @Override
    public Oglas update(Integer id,OglasRequest request) throws NotFoundException {
        if(!repository.existsById(id))
        {
            throw new NotFoundException();
        }
        OglasEntity entity=repository.findById(id).get();
        if(request.getSadrzaj()!=null && request.getSadrzaj().length()>0 && !request.getSadrzaj().equals(entity.getSadrzaj()))
        {
            entity.setSadrzaj(request.getSadrzaj());
        }
        entity=repository.saveAndFlush(entity);
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

    @Scheduled(cron = "0 12 * * ?")
    public void deleteOglas() throws NotFoundException {
        List<OglasEntity> oglasi=repository.findAll();
        for(OglasEntity oglas:oglasi)
        {
            Date datumObjave=oglas.getDatumObjave();
            LocalDate datum=datumObjave.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today=LocalDate.now();
            if(datum.isBefore(today.minusDays(14)))
            {
                delete(oglas.getId());
            }
        }
    }

    @Override
    public List<Oglas> getAllOglasiById(Integer id) {
        return repository.getAllOglasiById(id).stream().map(e->mapper.map(e,Oglas.class)).collect(Collectors.toList());
    }
}

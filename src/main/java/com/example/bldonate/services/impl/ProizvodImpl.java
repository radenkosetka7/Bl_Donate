package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.ConflictException;
import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Proizvod;
import com.example.bldonate.models.entities.JedinicaMjereEntity;
import com.example.bldonate.models.entities.KategorijaProizvodaEntity;
import com.example.bldonate.models.entities.ProizvodEntity;
import com.example.bldonate.models.requests.ProizvodRequest;
import com.example.bldonate.repositories.JedinicaMjereRepository;
import com.example.bldonate.repositories.KategorijaProizvodarRepository;
import com.example.bldonate.repositories.ProizvodRepository;
import com.example.bldonate.services.ProizvodService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProizvodImpl implements ProizvodService {

    private final ModelMapper mapper;
    private final ProizvodRepository repository;

    private final JedinicaMjereRepository jedinicaRepo;

    private final KategorijaProizvodarRepository kategorijaRepo;
    TypeMap<ProizvodEntity,Proizvod> property;

    @PersistenceContext
    private EntityManager manager;

    public ProizvodImpl(ProizvodRepository repository, ModelMapper mapper, JedinicaMjereRepository jedinicaRepo, KategorijaProizvodarRepository kategorijaRepo) {
        this.repository = repository;
        this.mapper = mapper;
        this.jedinicaRepo = jedinicaRepo;
        this.kategorijaRepo = kategorijaRepo;
        this.property =this.mapper.createTypeMap(ProizvodEntity.class,Proizvod.class);

        property.addMappings(
                m -> m.map(src->src.getKategorijaProizvoda().getNazivKategorije(),Proizvod::setKategorija)
        );

        property.addMappings(
                m -> m.map(src->src.getJedinicaMjere().getSkracenica(),Proizvod::setJedinica)
        );

    }


    @Override
    public List<Proizvod> getAll() {
        return repository.findAll().stream().map(e -> mapper.map(e, Proizvod.class)).collect(Collectors.toList());
    }

    @Override
    public Proizvod findById(Integer id) throws NotFoundException {

        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new), Proizvod.class);
    }

    @Override
    public Proizvod insert(@RequestBody ProizvodRequest request) throws Exception {
        ProizvodEntity entity = mapper.map(request, ProizvodEntity.class);
        KategorijaProizvodaEntity k=kategorijaRepo.findById(request.getKategorija()).get();
        entity.setKategorijaProizvoda(k);
        JedinicaMjereEntity j=jedinicaRepo.findById(request.getJedinica()).get();
        entity.setJedinicaMjere(j);
        entity.setId(null);
        entity = repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());
    }


    @Override
    public Proizvod update(Integer id,ProizvodRequest request) throws Exception {
        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }
        ProizvodEntity entity = repository.findById(id).get();
        // ProizvodEntity entity=repository.findById(id).orElseThrow(NotFoundException::new);
        if (request.getNaziv() != null && request.getNaziv().length() > 0 && !request.getNaziv().equals(entity.getNaziv())) {
            entity.setNaziv(request.getNaziv());
        }
        if (request.getRokUpotrebe() != null && !request.getRokUpotrebe().equals(entity.getRokUpotrebe())) {
            entity.setRokUpotrebe(request.getRokUpotrebe());
        }
        if (request.getKategorija() != null && request.getKategorija() > 0 && !request.getKategorija().equals(entity.getKategorijaProizvoda().getId()))
        {
            entity.setKategorijaProizvoda(kategorijaRepo.findById(request.getKategorija()).get());
          // entity.getKategorijaProizvoda().setId(request.getKategorija());
        }
        if(request.getJedinica()!=null && request.getJedinica()>0 && !request.getJedinica().equals(entity.getJedinicaMjere().getId()))
        {
            entity.setJedinicaMjere(jedinicaRepo.findById(request.getJedinica()).get());
        }
        entity=repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());
    }


    @Override
    public void delete(Integer id) throws NotFoundException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException();
        }


    }
}

package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.DonacijaStavka;
import com.example.bldonate.models.entities.DonacijaEntity;
import com.example.bldonate.models.entities.DonacijaStavkaEntity;
import com.example.bldonate.models.entities.ProizvodEntity;
import com.example.bldonate.models.entities.RezervacijaStavkaEntity;
import com.example.bldonate.models.requests.DonacijaStavkaRequest;
import com.example.bldonate.repositories.DonacijaRepository;
import com.example.bldonate.repositories.DonacijaStavkaRepository;
import com.example.bldonate.repositories.ProizvodRepository;
import com.example.bldonate.repositories.RezervacijaStavkaRepository;
import com.example.bldonate.services.DonacijaStavkaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DonacijaStavkaImpl implements DonacijaStavkaService {

    private final DonacijaStavkaRepository repository;
    private final ModelMapper mapper;

    private final RezervacijaStavkaRepository rezervacijaStavkaRepository;

    private final ProizvodRepository proizvodRepository;

    private final DonacijaRepository donacijaRepository;
    TypeMap<DonacijaStavkaEntity,DonacijaStavka> property;



    @PersistenceContext
    private EntityManager manager;

    public DonacijaStavkaImpl(DonacijaStavkaRepository repository, ModelMapper mapper, DonacijaRepository donacijaRepository, RezervacijaStavkaRepository rezervacijaStavkaRepository, ProizvodRepository proizvodRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.donacijaRepository = donacijaRepository;
        this.property =this.mapper.createTypeMap(DonacijaStavkaEntity.class,DonacijaStavka.class);

        property.addMappings(
                m -> m.map(src->src.getProizvod(), DonacijaStavka::setProizvodId)
        );

        this.rezervacijaStavkaRepository = rezervacijaStavkaRepository;
        this.proizvodRepository = proizvodRepository;
    }

    @Override
    public List<DonacijaStavka> getAll() {
        List<DonacijaStavkaEntity> donacijaStavke=repository.findAll();
        List<DonacijaStavka> donacijaStavkaDTO=repository.findAll().stream().map(e->mapper.map(e, DonacijaStavka.class)).collect(Collectors.toList());
        for(int i=0;i<donacijaStavke.size();i++)
        {
            donacijaStavkaDTO.get(i).getProizvodId().
                    setJedinica(donacijaStavke.get(i).getProizvod().getJedinicaMjere().getSkracenica());
            donacijaStavkaDTO.get(i).getProizvodId().
                    setKategorija(donacijaStavke.get(i).getProizvod().getKategorijaProizvoda().getNazivKategorije());
        }
        return donacijaStavkaDTO;
    }

    @Override
    public DonacijaStavka findById(Integer id) throws NotFoundException {
        DonacijaStavkaEntity stavkaEntity=repository.findById(id).get();
        DonacijaStavka stavka=mapper.map(stavkaEntity,DonacijaStavka.class);
        stavka.getProizvodId().setJedinica(stavkaEntity.getProizvod().getJedinicaMjere().getSkracenica());
        stavka.getProizvodId().setKategorija(stavkaEntity.getProizvod().getKategorijaProizvoda().getNazivKategorije());
        return stavka;
    }


    @Override
    public DonacijaStavka insert(DonacijaStavkaRequest request) throws NotFoundException {
            DonacijaStavkaEntity entity = mapper.map(request, DonacijaStavkaEntity.class);
            ProizvodEntity proizvod = proizvodRepository.findById(request.getProizvodId()).get();
            DonacijaEntity donacijaEntity = donacijaRepository.findById(request.getDonacijaId()).get();
            entity.setDonacija(donacijaEntity);
            entity.setProizvod(proizvod);
            entity = repository.saveAndFlush(entity);
            manager.refresh(entity);
            return findById(entity.getId());
    }

    @Override
    public DonacijaStavka update(Integer id, DonacijaStavkaRequest request) throws NotFoundException {

        if(!repository.existsById(id))
        {
            throw new NotFoundException();
        }
        DonacijaStavkaEntity entity=repository.findById(id).get();
        if(request.getKolicina()!=null && !request.getKolicina().equals(entity.getKolicina())
                && request.getKolicina().compareTo(BigDecimal.ZERO)>0)
        {
            entity.setKolicina(request.getKolicina());
            entity=repository.saveAndFlush(entity);
            manager.refresh(entity);
        }

        return findById(entity.getDonacija().getId());
    }

    @Override
    public void delete(Integer id) throws Exception {
        Integer proizvodId=repository.findById(id).get().getProizvod().getId();
        List<RezervacijaStavkaEntity> stavke=rezervacijaStavkaRepository.findAll();
        Boolean flag=stavke.stream().anyMatch(e->e.getDonacijaStavka().getId().equals(id));
        if(repository.existsById(id) && !flag) {
            repository.deleteById(id);
            //repository.delete(repository.findById(id).get().getProizvod());
            proizvodRepository.deleteById(proizvodId);
        }
        else
        {
            throw new Exception("Nije moguće obrisati stavku. Stavka je već rezervisana!");
        }

    }

}

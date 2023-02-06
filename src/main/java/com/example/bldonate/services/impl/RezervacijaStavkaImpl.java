package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.RezervacijaStavka;
import com.example.bldonate.models.entities.*;
import com.example.bldonate.models.requests.RezervacijaStavkaRequest;
import com.example.bldonate.repositories.DonacijaStavkaRepository;
import com.example.bldonate.repositories.ObavjestenjeRepository;
import com.example.bldonate.repositories.RezervacijaRepository;
import com.example.bldonate.repositories.RezervacijaStavkaRepository;
import com.example.bldonate.services.RezervacijaStavkaService;
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
public class RezervacijaStavkaImpl implements RezervacijaStavkaService{

    private final RezervacijaStavkaRepository repository;
    private final ModelMapper mapper;
    private final DonacijaStavkaRepository donacijaStavkaRepository;
    private final ObavjestenjeRepository obavjestenjeRepository;
    private final RezervacijaRepository rezervacijaRepository;
    private static int counter=0;

    TypeMap<RezervacijaStavkaEntity, RezervacijaStavka> property;

    @PersistenceContext
    private EntityManager manager;

    public RezervacijaStavkaImpl(RezervacijaStavkaRepository repository, ModelMapper mapper, DonacijaStavkaRepository donacijaStavkaRepository, ObavjestenjeRepository obavjestenjeRepository, RezervacijaRepository rezervacijaRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.donacijaStavkaRepository = donacijaStavkaRepository;

        this.property =this.mapper.createTypeMap(RezervacijaStavkaEntity.class,RezervacijaStavka.class);

        property.addMappings(
                m -> m.map(src->src.getDonacijaStavka(), RezervacijaStavka::setProizvod)
        );

        this.obavjestenjeRepository = obavjestenjeRepository;
        this.rezervacijaRepository = rezervacijaRepository;
    }

    @Override
    public List<RezervacijaStavka> getAll() {
        List<RezervacijaStavkaEntity> stavke=repository.findAll();
        List<RezervacijaStavka> stavkeDTO=repository.findAll().stream().map(e->mapper.map(e,RezervacijaStavka.class)).collect(Collectors.toList());
        for(int i=0;i<stavke.size();i++)
        {
            stavkeDTO.get(i).getProizvod().setNaziv(stavke.get(i).getDonacijaStavka().getProizvod().getNaziv());
            stavkeDTO.get(i).getProizvod().setRokUpotrebe(stavke.get(i).getDonacijaStavka().getProizvod().getRokUpotrebe());
            stavkeDTO.get(i).getProizvod().setJedinica(stavke.get(i).getDonacijaStavka().getProizvod().getJedinicaMjere().getSkracenica());
            stavkeDTO.get(i).getProizvod().setKategorija(stavke.get(i).getDonacijaStavka().getProizvod().getKategorijaProizvoda().getNazivKategorije());

        }

        return stavkeDTO;
    }

    @Override
    public RezervacijaStavka findById(Integer id) throws NotFoundException {

        RezervacijaStavkaEntity stavkaEntity = repository.findById(id).get();

        RezervacijaStavka stavka = mapper.map(stavkaEntity, RezervacijaStavka.class);
        stavka.getProizvod().setNaziv(stavkaEntity.getDonacijaStavka().getProizvod().getNaziv());
        stavka.getProizvod().setRokUpotrebe(stavkaEntity.getDonacijaStavka().getProizvod().getRokUpotrebe());
        stavka.getProizvod().setJedinica(stavkaEntity.getDonacijaStavka().getProizvod().getJedinicaMjere().getSkracenica());
        stavka.getProizvod().setKategorija(stavkaEntity.getDonacijaStavka().getProizvod().getKategorijaProizvoda().getNazivKategorije());
        return stavka;
    }

    @Override
    public RezervacijaStavka insert(RezervacijaStavkaRequest request) throws NotFoundException {
        RezervacijaStavkaEntity entity=mapper.map(request,RezervacijaStavkaEntity.class);
        DonacijaStavkaEntity donacijaStavka=donacijaStavkaRepository.findById(request.getDonacijaStavkaId()).get();
        entity.setId(null);
        entity.setDonacijaStavka(donacijaStavka);
        entity=repository.saveAndFlush(entity);
        donacijaStavka.setKolicina(donacijaStavka.getKolicina().subtract(request.getKolicina()));
        manager.refresh(entity);
        if(counter==0) {
            ObavjestenjeEntity obavjestenjeEntity = new ObavjestenjeEntity();
            obavjestenjeEntity.setSadrzaj("Korisnik " + entity.getRezervacija().getKorisnik().getIme() + " je kreirao rezervaciju.");
            obavjestenjeEntity.setProcitano(false);
            obavjestenjeEntity.setKorisnik(donacijaStavka.getDonacija().getKorisnik());
            obavjestenjeEntity = obavjestenjeRepository.saveAndFlush(obavjestenjeEntity);
            counter++;
        }
        return findById(entity.getId());
    }

    @Override
    public RezervacijaStavka update(Integer id,RezervacijaStavkaRequest request) throws NotFoundException {

        if(!repository.existsById(id))
        {
            throw new NotFoundException();
        }
        RezervacijaStavkaEntity entity=repository.findById(id).get();

        KorisnikEntity korisnik=entity.getRezervacija().getKorisnik();
        DonacijaStavkaEntity stavkaEntity=donacijaStavkaRepository.findById(entity.getDonacijaStavka().getId()).get();
        KorisnikEntity donator=stavkaEntity.getDonacija().getKorisnik();
        stavkaEntity.setKolicina(stavkaEntity.getKolicina().add(entity.getKolicina()));
        if(request.getKolicina()!=null && !request.getKolicina().equals(entity.getKolicina()) && request.getKolicina().compareTo(BigDecimal.ZERO)>0)
        {
            entity.setKolicina(request.getKolicina());
        }
        entity=repository.saveAndFlush(entity);
        manager.refresh(entity);
        stavkaEntity.setKolicina(stavkaEntity.getKolicina().subtract(request.getKolicina()));

        ObavjestenjeEntity obavjestenjeEntity=new ObavjestenjeEntity();
        obavjestenjeEntity.setSadrzaj("Korisnik " + korisnik.getIme() + " je izmijenio rezervaciju.");
        obavjestenjeEntity.setProcitano(false);
        obavjestenjeEntity.setKorisnik(donator);
        obavjestenjeEntity=obavjestenjeRepository.saveAndFlush(obavjestenjeEntity);
        return findById(id);

    }

    @Override
    public void delete(Integer id) throws NotFoundException {

        DonacijaStavkaEntity donacijaStavka=donacijaStavkaRepository.
                findById(repository.findById(id).get().getDonacijaStavka().getId()).get();
        BigDecimal kolicina=repository.findById(id).get().getKolicina();
        if(repository.existsById(id)) {
            donacijaStavka.setKolicina(donacijaStavka.getKolicina().add(kolicina));
            RezervacijaEntity rezervacija=repository.findById(id).get().getRezervacija();
            repository.deleteById(id);
            if(rezervacija.getRezervacijaStavke().size()==1)
            {

                rezervacijaRepository.delete(rezervacija);
            }
        }
        else
        {
            throw new NotFoundException();
        }
    }


}

package com.example.bldonate.services.impl;


import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Rezervacija;
import com.example.bldonate.models.entities.*;
import com.example.bldonate.models.requests.RezervacijaRequest;
import com.example.bldonate.repositories.*;
import com.example.bldonate.services.RezervacijaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RezervacijaImpl  implements RezervacijaService {

   private final RezervacijaRepository repository;
    private final ModelMapper mapper;

    private final KorisnikRepository korisnikRepository;
    TypeMap<RezervacijaEntity, Rezervacija> property;

    private final RezervacijaStavkaRepository rezervacijaStavkaRepository;
    private final DonacijaStavkaRepository donacijaStavkaRepository;

    private final ObavjestenjeRepository obavjestenjeRepository;

    @PersistenceContext
    private EntityManager manager;

    public RezervacijaImpl(RezervacijaRepository repository, ModelMapper mapper, KorisnikRepository korisnikRepository, RezervacijaStavkaRepository rezervacijaStavkaRepository, DonacijaStavkaRepository donacijaStavkaRepository, ObavjestenjeRepository obavjestenjeRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.korisnikRepository = korisnikRepository;
        this.property =this.mapper.createTypeMap(RezervacijaEntity.class,Rezervacija.class);

        property.addMappings(
                m -> m.map(src->src.getKorisnik().getIme(),Rezervacija::setKorisnikName)
        );

        this.rezervacijaStavkaRepository = rezervacijaStavkaRepository;
        this.donacijaStavkaRepository = donacijaStavkaRepository;
        this.obavjestenjeRepository = obavjestenjeRepository;
    }

    @Override
    public List<Rezervacija> getAll(Integer id) {
        List<RezervacijaEntity> entities=repository.getAllReservationByArchiveFlag(false,id);
        return getRezervacijasTempMethod(entities);
    }

    @Override
    public Rezervacija findById(Integer id) throws NotFoundException {
        RezervacijaEntity rezervacija=repository.findById(id).get();
        Rezervacija rezervacijaDTO=mapper.map(rezervacija,Rezervacija.class);
        for(int i=0;i<rezervacijaDTO.getStavke().size();i++)
        {
            rezervacijaDTO.getStavke().get(i).getProizvod().
                    setNaziv(rezervacija.getRezervacijaStavke().get(i).getDonacijaStavka().
                            getProizvod().getNaziv());
            rezervacijaDTO.getStavke().get(i).getProizvod().
            setRokUpotrebe(rezervacija.getRezervacijaStavke().get(i).getDonacijaStavka().
                            getProizvod().getRokUpotrebe());
            rezervacijaDTO.getStavke().get(i).getProizvod().
                    setJedinica(rezervacija.getRezervacijaStavke().get(i).getDonacijaStavka().
                            getProizvod().getJedinicaMjere().getSkracenica());
            rezervacijaDTO.getStavke().get(i).getProizvod().
                    setKategorija(rezervacija.getRezervacijaStavke().get(i).getDonacijaStavka().
                            getProizvod().getKategorijaProizvoda().getNazivKategorije());
        }
        return rezervacijaDTO;
    }

    @Override
    public Rezervacija insert(RezervacijaRequest request) throws NotFoundException {
        RezervacijaEntity entity=mapper.map(request,RezervacijaEntity.class);
        KorisnikEntity korisnik=korisnikRepository.findById(request.getKorisnikId()).get();
        entity.setId(null);
        entity.setKorisnik(korisnik);
        entity=repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());
    }

    @Override
    public Rezervacija update(Integer id,RezervacijaRequest request) throws NotFoundException {
        if(!repository.existsById(id))
        {
            throw new NotFoundException();
        }
        RezervacijaEntity entity=repository.findById(id).get();
        if(request.getArhivirana()!=null && !request.getArhivirana().equals(entity.getArhivirana()))
        {
            entity.setArhivirana(request.getArhivirana());
        }
        entity=repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());
    }

    @Override
    public List<Rezervacija> getAllReservations(Integer id) {
        List<RezervacijaEntity> entities=repository.getAllReservations(id);
        return getRezervacijasTempMethod(entities);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        KorisnikEntity korisnik=repository.findById(id).get().getKorisnik();
        KorisnikEntity donator=repository.findById(id).get().getRezervacijaStavke().get(0)
                .getDonacijaStavka().getDonacija().getKorisnik();
        if(repository.existsById(id)) {
            for(RezervacijaStavkaEntity stavka:repository.findById(id).get().getRezervacijaStavke())
            {
                DonacijaStavkaEntity donacijaStavka=donacijaStavkaRepository.
                        findById(stavka.getDonacijaStavka().getId()).get();
                BigDecimal kolicina=stavka.getKolicina();
                donacijaStavka.setKolicina(donacijaStavka.getKolicina().add(kolicina));
            }
            rezervacijaStavkaRepository.deleteAll(repository.findById(id).get().getRezervacijaStavke());
            repository.deleteById(id);
            ObavjestenjeEntity entity=new ObavjestenjeEntity();
        entity.setSadrzaj("Korisnik " + korisnik.getIme() + " je otkazao rezervaciju.");
        entity.setProcitano(false);
        entity.setKorisnik(donator);
        entity=obavjestenjeRepository.saveAndFlush(entity);
        }
        else
        {
            throw new NotFoundException();
        }

    }

    @Override
    public List<Rezervacija> getAllArchiveReservations(Integer id) {
        List<RezervacijaEntity> entities=repository.getAllArchiveReservations(id);
        return getRezervacijasTempMethod(entities);
    }

    @Override
    public List<Rezervacija> getAllArchiveRange(Integer id,Date pocetniDatum, Date krajnjiDatum) {
        List<RezervacijaEntity> entities=repository.getAllArchiveDateRange(id,pocetniDatum,krajnjiDatum);
        return getRezervacijasTempMethod(entities);
    }

    private List<Rezervacija> getRezervacijasTempMethod(List<RezervacijaEntity> entities) {
        return getRezervacijas(entities, mapper);
    }

    static List<Rezervacija> getRezervacijas(List<RezervacijaEntity> entities, ModelMapper mapper) {
        List<Rezervacija> rezervacijeDTO= entities.stream().map(e-> mapper.map(e,Rezervacija.class)).collect(Collectors.toList());
        for(int i=0;i<entities.size();i++)
        {
            for(int j=0;j<rezervacijeDTO.get(i).getStavke().size();j++)
            {
                rezervacijeDTO.get(i).getStavke().get(j).getProizvod().
                        setKategorija(entities.get(i).getRezervacijaStavke().get(j).
                                getDonacijaStavka().getProizvod().getKategorijaProizvoda().getNazivKategorije());
                rezervacijeDTO.get(i).getStavke().get(j).getProizvod().
                        setNaziv(entities.get(i).getRezervacijaStavke().get(j).
                                getDonacijaStavka().getProizvod().getNaziv());
                rezervacijeDTO.get(i).getStavke().get(j).getProizvod().
                        setRokUpotrebe(entities.get(i).getRezervacijaStavke().get(j).
                                getDonacijaStavka().getProizvod().getRokUpotrebe());
                rezervacijeDTO.get(i).getStavke().get(j).getProizvod().
                        setJedinica(entities.get(i).getRezervacijaStavke().get(j).
                                getDonacijaStavka().getProizvod().getJedinicaMjere().getSkracenica());
            }
        }
        return rezervacijeDTO;
    }

}

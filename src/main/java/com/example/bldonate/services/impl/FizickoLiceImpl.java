package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Donacija;
import com.example.bldonate.models.dto.FizickoLice;
import com.example.bldonate.models.dto.Rezervacija;
import com.example.bldonate.models.dto.RezervacijaStavka;
import com.example.bldonate.models.entities.*;
import com.example.bldonate.models.requests.DonatorRequest;
import com.example.bldonate.models.requests.FizickoLiceRequest;
import com.example.bldonate.repositories.FizickoLiceRepository;
import com.example.bldonate.repositories.RezervacijaRepository;
import com.example.bldonate.repositories.RezervacijaStavkaRepository;
import com.example.bldonate.services.DonatorService;
import com.example.bldonate.services.FizickoLiceService;
import com.example.bldonate.services.ObavjestenjeService;
import com.example.bldonate.services.RezervacijaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FizickoLiceImpl implements FizickoLiceService {
    private final FizickoLiceRepository repository;
    private final ModelMapper mapper;
    private final DonatorService donatorService;
    private final ObavjestenjeService obavjestenjeService;
    private final RezervacijaStavkaRepository rezervacijaStavkaRepository;
    private final RezervacijaRepository rezervacijaRepository;
    @PersistenceContext
    private EntityManager manager;

    public FizickoLiceImpl(FizickoLiceRepository repository, ModelMapper mapper, DonatorService donatorService, ObavjestenjeService obavjestenjeService, RezervacijaStavkaRepository rezervacijaStavkaRepository, RezervacijaRepository rezervacijaRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.donatorService = donatorService;
        this.obavjestenjeService = obavjestenjeService;
        this.rezervacijaStavkaRepository = rezervacijaStavkaRepository;
        this.rezervacijaRepository = rezervacijaRepository;
    }

    @Override
    public List<FizickoLice> getAll() {
        return repository.getAllApprovedUsers().stream().map(e -> mapper.map(e, FizickoLice.class)).collect(Collectors.toList());

    }

    @Override
    public FizickoLice findById(Integer id) throws NotFoundException {
        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new), FizickoLice.class);
    }

    @Override
    public FizickoLice insert(FizickoLiceRequest request) throws NotFoundException {
        DonatorRequest donatorRequest = request.getDonator();
        DonatorEntity don = donatorService.insert(donatorRequest);
        FizickoLiceEntity entity = mapper.map(request, FizickoLiceEntity.class);
        entity.setId(null);
        entity.setDonator(don);
        entity = repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());
    }

    @Override
    public FizickoLice update(Integer id, FizickoLiceRequest request) throws NotFoundException {
        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }
        DonatorRequest donatorRequest = request.getDonator();
        FizickoLiceEntity fizickoLice = repository.findById(id).get();
        fizickoLice.setDonator(donatorService.update(id, donatorRequest));
        if (request.getJmbg() != null && request.getJmbg().length() > 0 && !request.getJmbg().equals(fizickoLice.getJmbg())) {
            fizickoLice.setJmbg(request.getJmbg());
        }
        if (request.getPrezime() != null && request.getPrezime().length() > 0 && !request.getPrezime().equals(fizickoLice.getPrezime())) {
            fizickoLice.setPrezime(request.getPrezime());
        }
        fizickoLice = repository.saveAndFlush(fizickoLice);
        manager.refresh(fizickoLice);
        return findById(fizickoLice.getId());
    }

    @Override
    public void delete(Integer id) throws Exception {

        List<DonacijaEntity> donacije=repository.findById(id).get().getDonator().getDonacije();
        List<RezervacijaStavkaEntity> rezervacijaStavkaEntities=rezervacijaStavkaRepository.findAll();
        boolean flag=false;
        for(DonacijaEntity donacija:donacije)
        {
            if(rezervacijaStavkaEntities.stream().anyMatch(e->!e.getRezervacija().getArhivirana() &&
                    e.getDonacijaStavka().getDonacija().getId().equals(donacija.getId())))
            {
                flag=true;
                break;
            }
        }

        if(repository.existsById(id) && !flag) {

            for(ObavjestenjeEntity obavjestenje:repository.findById(id).get().getDonator().getObavjestenjes())
            {
                obavjestenjeService.delete(obavjestenje.getId());
            }
            for(DonacijaEntity donacija:repository.findById(id).get().getDonator().getDonacije())
            {
                donatorService.delete(donacija.getId());
            }
            repository.deleteById(id);
            donatorService.delete(id);
        }
        else
        {
            throw new Exception("Nije moguće obrisati nalog. Postoje donacije koje su rezervisane!");
        }
    }

    @Override
    public List<FizickoLice> getAllUnapprovedUsers() {
        return repository.getAllUnApprovedUsers().stream().map(e -> mapper.map(e, FizickoLice.class)).collect(Collectors.toList());

    }

    public List<Rezervacija> getAllReservations(Integer id)
    {
        List<RezervacijaEntity> rezervacije=rezervacijaRepository.findAll();
        rezervacije.stream().filter(e->e.getRezervacijaStavke().get(0).
                getDonacijaStavka().getDonacija().getDonator().getId().equals(id) && !e.getArhivirana())
                .collect(Collectors.toList());
        List<Rezervacija> rezervacijeDTO= rezervacije.stream().map(e->mapper.map(e,Rezervacija.class)).collect(Collectors.toList());
        for(int i=0;i<rezervacije.size();i++)
        {
            for(int j=0;j<rezervacijeDTO.get(i).getStavke().size();j++)
            {
                rezervacijeDTO.get(i).getStavke().get(j).getProizvod().
                        setKategorija(rezervacije.get(i).getRezervacijaStavke().get(j).
                                getDonacijaStavka().getProizvod().getKategorijaProizvoda().getNazivKategorije());
                rezervacijeDTO.get(i).getStavke().get(j).getProizvod().
                        setNaziv(rezervacije.get(i).getRezervacijaStavke().get(j).
                                getDonacijaStavka().getProizvod().getNaziv());
                rezervacijeDTO.get(i).getStavke().get(j).getProizvod().
                        setRokUpotrebe(rezervacije.get(i).getRezervacijaStavke().get(j).
                                getDonacijaStavka().getProizvod().getRokUpotrebe());
                rezervacijeDTO.get(i).getStavke().get(j).getProizvod().
                        setJedinica(rezervacije.get(i).getRezervacijaStavke().get(j).
                                getDonacijaStavka().getProizvod().getJedinicaMjere().getSkracenica());
            }
        }
        return rezervacijeDTO;

    }
}

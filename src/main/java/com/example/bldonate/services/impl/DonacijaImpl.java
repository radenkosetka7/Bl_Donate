package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Donacija;
import com.example.bldonate.models.dto.Rezervacija;
import com.example.bldonate.models.entities.*;
import com.example.bldonate.models.requests.DonacijaRequest;
import com.example.bldonate.repositories.DonacijaRepository;
import com.example.bldonate.repositories.DonacijaStavkaRepository;
import com.example.bldonate.repositories.DonatorRepository;
import com.example.bldonate.repositories.RezervacijaStavkaRepository;
import com.example.bldonate.services.DonacijaService;
import com.example.bldonate.services.DonacijaStavkaService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DonacijaImpl implements DonacijaService {


    private final DonacijaRepository repository;
    private final ModelMapper mapper;
    private final DonatorRepository donatorRepository;
    TypeMap<DonacijaEntity, Donacija> property;
    private final DonacijaStavkaRepository donacijaStavkaRepository;
    private final RezervacijaStavkaRepository rezervacijaStavkaRepository;
    private final DonacijaStavkaService donacijaStavkaService;
    @PersistenceContext
    private EntityManager manager;

    public DonacijaImpl(DonacijaRepository repository, ModelMapper mapper, DonatorRepository donatorRepository, DonacijaStavkaRepository donacijaStavkaRepository, RezervacijaStavkaRepository rezervacijaStavkaRepository, DonacijaStavkaService donacijaStavkaService) {
        this.repository = repository;
        this.mapper = mapper;
        this.property =this.mapper.createTypeMap(DonacijaEntity.class, Donacija.class);


        property.addMappings(
                m -> m.map(src->src.getDonator().getIme(),Donacija::setDonator));

        this.donatorRepository = donatorRepository;
        this.donacijaStavkaRepository = donacijaStavkaRepository;
        this.rezervacijaStavkaRepository = rezervacijaStavkaRepository;
        this.donacijaStavkaService = donacijaStavkaService;
    }

    @Override
    public List<Donacija> getAll() {
        List<DonacijaEntity> donacije=repository.findAll();
        donacije.stream().filter(e->!e.getArhivirana()).collect(Collectors.toList());
        List<Donacija> donacijeDTO=donacije.stream().map(e->mapper.map(e,Donacija.class)).collect(Collectors.toList());
        donacijeDTO.stream().filter(e->!e.getArhivirana()).collect(Collectors.toList());
        for(int i=0;i<donacije.size();i++)
        {
            if(donacije.get(i).getDonator().getFizickoLice()!=null)
            {
                donacijeDTO.get(i).setDonator(donacije.get(i).getDonator().getIme()+ " "+
                        donacije.get(i).getDonator().getFizickoLice().getPrezime());
            }
            for(int j=0;j<donacijeDTO.get(i).getStavke().size();j++)
            {
                donacijeDTO.get(i).getStavke().get(j).getProizvodId().
                        setKategorija(donacije.get(i).getDonacijaStavke().get(j).
                                getProizvod().getKategorijaProizvoda().getNazivKategorije());
                donacijeDTO.get(i).getStavke().get(j).getProizvodId().
                        setJedinica(donacije.get(i).getDonacijaStavke().get(j).getProizvod().
                                getJedinicaMjere().getSkracenica());
            }
        }
        return donacijeDTO;
    }

    @Override
    public Donacija findById(Integer id) throws NotFoundException {
        DonacijaEntity donacijaEntity=repository.findById(id).get();
        Donacija donacija=mapper.map(donacijaEntity,Donacija.class);
        for(int i=0;i<donacija.getStavke().size();i++)
        {
           donacija.getStavke().get(i).getProizvodId().
                   setKategorija(donacijaEntity.getDonacijaStavke().get(i).
                           getProizvod().getKategorijaProizvoda().getNazivKategorije());
           donacija.getStavke().get(i).getProizvodId().
                   setJedinica(donacijaEntity.getDonacijaStavke().get(i).getProizvod().
                           getJedinicaMjere().getSkracenica());
        }
        if(donacijaEntity.getDonator().getFizickoLice()!=null)
        {
            donacija.setDonator(donacijaEntity.getDonator().getIme() + " " +
                    donacijaEntity.getDonator().getFizickoLice().getPrezime());
        }
        return donacija;
    }

    @Override
    public Donacija insert(DonacijaRequest request) throws NotFoundException {
        DonacijaEntity entity=mapper.map(request,DonacijaEntity.class);
        DonatorEntity donator=donatorRepository.findById(request.getDonatorName()).get();
        entity.setId(null);
        entity.setDonator(donator);
        entity=repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());
    }

    @Override
    public Donacija update(Integer id,DonacijaRequest request) throws NotFoundException {
        if(!repository.existsById(id))
        {
            throw new NotFoundException();
        }
        DonacijaEntity entity=repository.findById(id).get();

        if(!entity.getArhivirana()) {
            if (request.getTerminPreuzimanja() != null && !request.getTerminPreuzimanja().equals(entity.getTerminPreuzimanja())) {
                entity.setTerminPreuzimanja(request.getTerminPreuzimanja());
            }
            if (request.getAdresa() != null && request.getAdresa().length() > 0 && !request.getAdresa().equals(entity.getAdresa())) {
                entity.setAdresa(request.getAdresa());
            }
            if (request.getBrojTelefona() != null && request.getBrojTelefona().length() > 0 && !request.getBrojTelefona().equals(entity.getBrojTelefona())) {
                entity.setBrojTelefona(request.getBrojTelefona());
            }
            if (request.getNapomena() != null && !request.getNapomena().equals(entity.getNapomena()) && request.getNapomena().length() > 0) {
                entity.setNapomena(request.getNapomena());
            }
            if (request.getArhivirana() != null && !request.getArhivirana().equals(entity.getArhivirana())) {
                entity.setArhivirana(request.getArhivirana());
            }
            entity = repository.saveAndFlush(entity);
            manager.refresh(entity);
        }

        return findById(entity.getId());
    }

    @Override
    public void delete(Integer id) throws Exception {
        List<RezervacijaStavkaEntity> stavke=rezervacijaStavkaRepository.findAll();
        Boolean flag=stavke.stream().anyMatch(e->e.getDonacijaStavka().getDonacija().getId().equals(id));
        if(repository.existsById(id) && !flag) {
            for(DonacijaStavkaEntity entity:repository.findById(id).get().getDonacijaStavke())
            {
                donacijaStavkaService.delete(entity.getId());
            }
            //donacijaStavkaRepository.deleteAll(repository.findById(id).get().getDonacijaStavke());
            repository.deleteById(id);
        }
        else
        {
            throw new Exception("Nije moguće obrisati donaciju. Donacija je već rezervisana!");
        }
    }

    @Override
    public List<Donacija> getAllDonacijaByDonorId(Integer id) {
        List<DonacijaEntity> donacije=repository.getAllDonacijaByDonorId(id);
        List<Donacija> donacijeDTO=donacije.stream().map(e->mapper.map(e,Donacija.class)).collect(Collectors.toList());
        for(int i=0;i<donacije.size();i++)
        {
            if(donacije.get(i).getDonator().getFizickoLice()!=null)
            {
                donacijeDTO.get(i).setDonator(donacije.get(i).getDonator().getIme()+ " "+
                        donacije.get(i).getDonator().getFizickoLice().getPrezime());
            }
            for(int j=0;j<donacijeDTO.get(i).getStavke().size();j++)
            {
                donacijeDTO.get(i).getStavke().get(j).getProizvodId().
                        setKategorija(donacije.get(i).getDonacijaStavke().get(j).
                                getProizvod().getKategorijaProizvoda().getNazivKategorije());
                donacijeDTO.get(i).getStavke().get(j).getProizvodId().
                        setJedinica(donacije.get(i).getDonacijaStavke().get(j).getProizvod().
                                getJedinicaMjere().getSkracenica());
            }
        }
        return donacijeDTO;
    }

    @Override
    public List<Donacija> getAllArchiveDonations(Integer id) {
        List<DonacijaEntity> donacije=repository.getAllArchiveDonations(id);
        List<Donacija> donacijeDTO=donacije.stream().map(e->mapper.map(e,Donacija.class)).collect(Collectors.toList());
        for(int i=0;i<donacije.size();i++)
        {
            if(donacije.get(i).getDonator().getFizickoLice()!=null)
            {
                donacijeDTO.get(i).setDonator(donacije.get(i).getDonator().getIme()+ " "+
                        donacije.get(i).getDonator().getFizickoLice().getPrezime());
            }
            for(int j=0;j<donacijeDTO.get(i).getStavke().size();j++)
            {
                donacijeDTO.get(i).getStavke().get(j).getProizvodId().
                        setKategorija(donacije.get(i).getDonacijaStavke().get(j).
                                getProizvod().getKategorijaProizvoda().getNazivKategorije());
                donacijeDTO.get(i).getStavke().get(j).getProizvodId().
                        setJedinica(donacije.get(i).getDonacijaStavke().get(j).getProizvod().
                                getJedinicaMjere().getSkracenica());
            }
        }
        return donacijeDTO;
    }

    @Override
    public List<Donacija> getAllArchiveRange(Integer id, Date pocetniDatum, Date krajnjiDatum) {
        List<DonacijaEntity> donacije=repository.getAllArchiveDateRange(id,pocetniDatum,krajnjiDatum);
        List<Donacija> donacijeDTO=donacije.stream().map(e->mapper.map(e,Donacija.class)).collect(Collectors.toList());
        for(int i=0;i<donacije.size();i++)
        {
            if(donacije.get(i).getDonator().getFizickoLice()!=null)
            {
                donacijeDTO.get(i).setDonator(donacije.get(i).getDonator().getIme()+ " "+
                        donacije.get(i).getDonator().getFizickoLice().getPrezime());
            }
            for(int j=0;j<donacijeDTO.get(i).getStavke().size();j++)
            {
                donacijeDTO.get(i).getStavke().get(j).getProizvodId().
                        setKategorija(donacije.get(i).getDonacijaStavke().get(j).
                                getProizvod().getKategorijaProizvoda().getNazivKategorije());
                donacijeDTO.get(i).getStavke().get(j).getProizvodId().
                        setJedinica(donacije.get(i).getDonacijaStavke().get(j).getProizvod().
                                getJedinicaMjere().getSkracenica());
            }
        }
        return donacijeDTO;
    }

    @Scheduled(cron = "0 12 * * ?")
    public void sendObavjestenjeOPreuzimanju() throws NotFoundException {
        List<DonacijaEntity> donacije=repository.findAll();
        donacije.stream().filter(e->!e.getArhivirana()).collect(Collectors.toList());
        for(DonacijaEntity donacija:donacije)
        {
            Date rokPreuzimanja=donacija.getTerminPreuzimanja();
            LocalDate datum=rokPreuzimanja.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate today=LocalDate.now();
            KorisnikEntity korisnik=donacija.getDonacijaStavke().get(0).getRezervacijaStavkas().get(0)
                    .getRezervacija().getKorisnik();
            if(datum.isBefore(today.plusDays(2)))
            {
                ObavjestenjeEntity obavjestenjeEntity=new ObavjestenjeEntity();
                obavjestenjeEntity.setProcitano(false);
                obavjestenjeEntity.setSadrzaj("Termin za preuzimanje rezervacije uskoro ističe.");
                obavjestenjeEntity.setKorisnik(korisnik);
            }
        }
    }
}

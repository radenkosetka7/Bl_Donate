package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Korisnik;
import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.entities.ObavjestenjeEntity;
import com.example.bldonate.models.entities.OglasEntity;
import com.example.bldonate.models.entities.RezervacijaEntity;
import com.example.bldonate.models.requests.KorisnikRequest;
import com.example.bldonate.repositories.KorisnikRepository;
import com.example.bldonate.services.KorisnikService;
import com.example.bldonate.services.ObavjestenjeService;
import com.example.bldonate.services.OglasService;
import com.example.bldonate.services.RezervacijaService;
import com.example.bldonate.util.SecureUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class KorisnikImpl implements KorisnikService {

   private final KorisnikRepository repository;
    private final ModelMapper mapper;

    private final RezervacijaService rezervacijaService;
    private final OglasService oglasService;
    private final ObavjestenjeService obavjestenjeService;
    @PersistenceContext
    private EntityManager manager;

    public KorisnikImpl(KorisnikRepository repository, ModelMapper mapper, RezervacijaService rezervacijaService, OglasService oglasService, ObavjestenjeService obavjestenjeService) {
        this.repository = repository;
        this.mapper = mapper;
        this.rezervacijaService = rezervacijaService;
        this.oglasService = oglasService;
        this.obavjestenjeService = obavjestenjeService;
    }


    @Override
    public List<Korisnik> getAll() {
        return repository.getAllApprovedUsers().stream().map(e->mapper.map(e,Korisnik.class)).collect(Collectors.toList());
    }


    @Override
    public Korisnik findById(Integer id) throws NotFoundException {
        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new),Korisnik.class);
    }


    @Override
    public Korisnik insert(KorisnikRequest request) throws NotFoundException {
        KorisnikEntity entity=mapper.map(request, KorisnikEntity.class);
        entity.setId(null);
        byte[] salt=entity.getLozinka().getBytes(StandardCharsets.UTF_8);
        String hash= SecureUtils.getSecurePassword(request.getLozinka(),salt);
        entity.setLozinka(hash);
        entity=repository.saveAndFlush(entity);
        manager.refresh(entity);
        return findById(entity.getId());
    }

    @Override
    public Korisnik update(Integer id,KorisnikRequest request) throws NotFoundException {
        if(!repository.existsById(id))
        {
            throw new NotFoundException();
        }
        KorisnikEntity entity=repository.findById(id).get();
        if(request.getNaziv()!=null && request.getNaziv().length()>0 && !request.getNaziv().equals(entity.getNaziv()))
        {
            entity.setNaziv(request.getNaziv());
        }
        if(request.getAdresa()!=null && request.getAdresa().length()>0 && !request.getAdresa().equals(entity.getAdresa()))
        {
            entity.setAdresa(request.getAdresa());
        }
        if(request.getEmail()!=null && request.getEmail().length()>0 && !request.getEmail().equals(entity.getEmail()))
        {
            entity.setEmail(request.getEmail());
        }
        if(request.getBrojTelefona()!=null && request.getBrojTelefona().length()>0 && !request.getBrojTelefona().equals(entity.getBrojTelefona()))
        {
            entity.setBrojTelefona(request.getBrojTelefona());
        }
        if(request.getOdoboren()!=null && !request.getOdoboren().equals(entity.getOdoboren()))
        {
            entity.setOdoboren(request.getOdoboren());
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
        return findById(entity.getId());
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        if(repository.existsById(id)) {
            for(RezervacijaEntity rezervacija:repository.findById(id).get().getRezervacije())
            {
                rezervacijaService.delete(rezervacija.getId());
            }
            for(OglasEntity oglas:repository.findById(id).get().getOglasis())
            {
                oglasService.delete(oglas.getId());
            }
            for(ObavjestenjeEntity obavjestenje:repository.findById(id).get().getObavjestenjes())
            {
                obavjestenjeService.delete(obavjestenje.getId());
            }
            repository.deleteById(id);
        }
        else
        {
            throw new NotFoundException();
        }
    }

    @Override
    public List<Korisnik> getAllUnapprovedUsers() {
        return repository.getAllUnapprovedUsers().stream().map(e->mapper.map(e,Korisnik.class)).collect(Collectors.toList());

    }

}

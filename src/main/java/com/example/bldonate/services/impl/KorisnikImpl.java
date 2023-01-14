package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.ConflictException;
import com.example.bldonate.exceptions.ForbiddenException;
import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Korisnik;
import com.example.bldonate.models.dto.LoginResponse;
import com.example.bldonate.models.dto.Oglas;
import com.example.bldonate.models.dto.Rezervacija;
import com.example.bldonate.models.entities.*;
import com.example.bldonate.models.enums.Role;
import com.example.bldonate.models.enums.UserStatus;
import com.example.bldonate.models.requests.ChangeRoleRequest;
import com.example.bldonate.models.requests.ChangeStatusRequest;
import com.example.bldonate.models.requests.SignUpRequest;
import com.example.bldonate.models.requests.UserUpdateRequest;
import com.example.bldonate.models.requests.ChangePasswordRequest;
import com.example.bldonate.repositories.KorisnikRepository;
import com.example.bldonate.repositories.RezervacijaRepository;
import com.example.bldonate.repositories.RezervacijaStavkaRepository;
import com.example.bldonate.services.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Status;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.bldonate.services.impl.RezervacijaImpl.getRezervacijas;

@Service
@Transactional
public class KorisnikImpl implements KorisnikService {

    private final KorisnikRepository repository;
    private final ModelMapper mapper;
    //private final Class<KorisnikEntity> entityClass;
    private final PasswordEncoder passwordEncoder;
    private final RezervacijaStavkaRepository rezervacijaStavkaRepository;
    private final DonacijaService donacijaService;
    private final RezervacijaService rezervacijaService;
    private final OglasService oglasService;
    private final ObavjestenjeService obavjestenjeService;
    private final RezervacijaRepository rezervacijaRepository;
    private final EmailService emailService;


    @Value("${authorization.default.username:}")
    private String defaultUsername;
    @Value("${authorization.default.first-name:}")
    private String defaultFirstName;
    @Value("${authorization.default.last-name:}")
    private String defaultLastName;
    @Value("${authorization.default.telephone:}")
    private String defaultTelephone;
    @Value("${authorization.default.password:}")
    private String defaultPassword;
    @Value("${authorization.default.email:}")
    private String defaultEmail;
    @Value("${spring.profiles.active:unknown}")
    private String activeProfile;


    @PersistenceContext
    private EntityManager manager;

    public KorisnikImpl(KorisnikRepository repository, ModelMapper mapper, RezervacijaService rezervacijaService, OglasService oglasService, ObavjestenjeService obavjestenjeService,
                        RezervacijaRepository rezervacijaRepository, RezervacijaStavkaRepository rezervacijaStavkaRepository, DonacijaService donacijaService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.repository = repository;
        this.mapper = mapper;
        this.rezervacijaService = rezervacijaService;
        this.oglasService = oglasService;
        this.obavjestenjeService = obavjestenjeService;
        this.rezervacijaRepository = rezervacijaRepository;
        this.rezervacijaStavkaRepository = rezervacijaStavkaRepository;
        this.donacijaService = donacijaService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @PostConstruct
    public void postConstruct() {

        if (repository.count() == 0) {
            KorisnikEntity userEntity = new KorisnikEntity();
            userEntity.setKorisnickoIme(defaultUsername);
            userEntity.setLozinka(passwordEncoder.encode(defaultPassword));
            userEntity.setEmail(defaultEmail);
            userEntity.setIme(defaultFirstName);
            userEntity.setPrezime(defaultLastName);
            userEntity.setStatus(KorisnikEntity.Status.ACTIVE);
            userEntity.setBrojTelefona(defaultTelephone);
            userEntity.setRola(Role.ADMIN);
            repository.saveAndFlush(userEntity);
        }
    }

    public void signUp(SignUpRequest request) {
        if (repository.existsByKorisnickoIme(request.getIme()))
            throw new ConflictException();
        KorisnikEntity entity = mapper.map(request, KorisnikEntity.class);
        entity.setLozinka(passwordEncoder.encode(entity.getLozinka()));
        entity.setStatus(KorisnikEntity.Status.REQUESTED);
        entity.setRola(request.getRole());
        Korisnik user = insert(entity, Korisnik.class);
    }

    @Override
    public List<Korisnik> getAll() {
        List<Korisnik> allUsers=repository.getAllByStatus(KorisnikEntity.Status.ACTIVE)
                .stream().filter(e->!e.getRola().equals(Role.ADMIN))
                .map(e -> mapper.map(e, Korisnik.class)).collect(Collectors.toList());
        allUsers.addAll(repository.getAllByStatus( KorisnikEntity.Status.BLOCKED)
                .stream().map(e -> mapper.map(e, Korisnik.class)).collect(Collectors.toList()));
        return allUsers;
    }

    @Override
    public List<Korisnik> getAllDonors(Integer id) {
        KorisnikEntity korisnikEntity=repository.findById(id).get();
        if(korisnikEntity.getRola().equals(Role.DONATOR))
        {
            return repository.getAllByStatusAndRola(KorisnikEntity.Status.ACTIVE, Role.DONATOR)
                    .stream().filter(e->!e.getId().equals(korisnikEntity.getId())).
                    map(e -> mapper.map(e, Korisnik.class)).collect(Collectors.toList());
        }
        else
        {
            return repository.getAllByStatusAndRola(KorisnikEntity.Status.ACTIVE, Role.DONATOR)
                    .stream().map(e -> mapper.map(e, Korisnik.class)).collect(Collectors.toList());
        }
    }


    @Override
    public LoginResponse findById(Integer id, Class<LoginResponse> response) throws NotFoundException {
        return mapper.map(repository.findById(id).orElseThrow(NotFoundException::new), LoginResponse.class);
    }

    @Override
    public Korisnik insert(KorisnikEntity korisnikEntity, Class<Korisnik> korisnik) throws NotFoundException {
        //KorisnikEntity entity=mapper.map(korisnik,entityClass);
        korisnikEntity.setId(null);
        korisnikEntity = repository.saveAndFlush(korisnikEntity);
        manager.refresh(korisnikEntity);
        return mapper.map(korisnikEntity, korisnik);
    }

   /* @Override
    public Korisnik update(Integer id, KorisnikEntity korisnikEntity, Class<Korisnik> korisnik) throws NotFoundException {
        if(!repository.existsById(id))
        {
            throw new NotFoundException();
        }
        KorisnikEntity entity=mapper.map(korisnikEntity,entityClass);
        entity.setId(id);
        entity=repository.saveAndFlush(entity);
        manager.refresh(entity);
        return mapper.map(entity,korisnik);
    }*/

    @Override
    public List<Korisnik> getAllUnapprovedUsers() {
        List<Korisnik> allRequests=repository.getAllByStatusAndRola(KorisnikEntity.Status.REQUESTED, Role.DONATOR).
                stream().map(e -> mapper.map(e, Korisnik.class)).collect(Collectors.toList());
        allRequests.addAll(repository.getAllByStatusAndRola(KorisnikEntity.Status.REQUESTED, Role.KORISNIK).
                stream().map(e -> mapper.map(e, Korisnik.class)).collect(Collectors.toList()));

        return allRequests;
    }

    public KorisnikEntity findEntityById(Integer id) {
        return repository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Korisnik update(Integer id, UserUpdateRequest user) throws Exception {
        if (repository.existsByKorisnickoImeAndIdNot(user.getIme(), id))
            throw new ConflictException();
        KorisnikEntity entity = repository.findById(id).get();
        if (user.getIme() != null && user.getIme().length() > 0 && !user.getIme().equals(entity.getIme())) {
            entity.setIme(user.getIme());
        }
        if (user.getKorisnickoIme() != null && user.getKorisnickoIme().length() > 0 &&
                !user.getKorisnickoIme().equals(entity.getKorisnickoIme())) {
            entity.setKorisnickoIme(user.getKorisnickoIme());
        }
        if (user.getEmail() != null && user.getEmail().length() > 0 && !user.getEmail().equals(entity.getEmail())) {
            entity.setEmail(user.getEmail());
        }
        if (user.getBrojTelefona() != null && user.getBrojTelefona().length() > 0 &&
                !user.getBrojTelefona().equals(entity.getBrojTelefona())) {
            entity.setBrojTelefona(user.getBrojTelefona());
        }
        if (user.getLozinka() != null && user.getLozinka().length() > 0 && !user.getLozinka().equals(entity.getLozinka())) {
            entity.setLozinka(passwordEncoder.encode(user.getLozinka()));
        }
        entity.setId(id);
        entity = repository.saveAndFlush(entity);
        manager.refresh(entity);
        return mapper.map(entity, Korisnik.class);

    }

    @Override
    public void changeStatus(Integer userId, ChangeStatusRequest request) throws Exception {
        KorisnikEntity.Status status=repository.findById(userId).get().getStatus();
        KorisnikEntity entity = repository.findById(userId).get();
        if (status.toString().equals(UserStatus.REQUESTED.toString()) && UserStatus.ACTIVE.equals(request.getStatus())) {
            entity.setStatus(KorisnikEntity.Status.ACTIVE);
            emailService.sendSimpleMailApproved(entity.getEmail());
        }
        if (status.toString().equals(UserStatus.BLOCKED.toString()) && UserStatus.ACTIVE.equals(request.getStatus())) {
            entity.setStatus(KorisnikEntity.Status.ACTIVE);
            emailService.sendSimpleMailApproved(entity.getEmail());
        }
        if (status.toString().equals(UserStatus.REQUESTED.toString()) && UserStatus.BLOCKED.equals(request.getStatus())) {
            entity.setStatus(KorisnikEntity.Status.BLOCKED);
            emailService.sendSimpleMailNotApproved(entity.getEmail());
        }
        if (UserStatus.REQUESTED.equals(request.getStatus())) {
            throw new ForbiddenException();
        }
        //entity.setStatus(mapper.map(request.getStatus(), KorisnikEntity.Status.class));
        repository.saveAndFlush(entity);
    }

    @Override
    public void changeRole(Integer userId, ChangeRoleRequest request) {
        KorisnikEntity entity = repository.findById(userId).get();

        entity.setRola(request.getRole());
        repository.saveAndFlush(entity);
    }

    @Override
    public List<Rezervacija> getAllReservationsDonor(Integer id) {
        List<RezervacijaStavkaEntity> rezervacijeStavke = rezervacijaStavkaRepository.getAllReservationsByDonor(id);
        List<RezervacijaEntity> rezervacije=new ArrayList<>();
        for(int i=0;i<rezervacijeStavke.size();i++)
        {
            rezervacije.add(rezervacijeStavke.get(i).getRezervacija());
        }
        return getRezervacijas(rezervacije, mapper);

    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        if (repository.existsById(id)) {
            KorisnikEntity korisnikEntity = repository.findById(id).get();
            List<RezervacijaEntity> rezevacije = repository.findById(id).get().getRezervacije();
            rezevacije.stream()
                    .filter(e -> e.getKorisnik().equals(korisnikEntity) && !e.getArhivirana())
                    .collect(Collectors.toList());
            for (RezervacijaEntity rezervacija : rezevacije) {
                rezervacijaService.delete(rezervacija.getId());
            }
            for (OglasEntity oglas : repository.findById(id).get().getOglasi()) {
                oglasService.delete(oglas.getId());
            }
            for (ObavjestenjeEntity obavjestenje : repository.findById(id).get().getObavjestenja()) {
                obavjestenjeService.delete(obavjestenje.getId());
            }
            korisnikEntity.setStatus(KorisnikEntity.Status.BLOCKED);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void deleteDonor(Integer id) throws Exception {
        if (repository.existsById(id)) {
            KorisnikEntity korisnikEntity = repository.findById(id).get();
            List<DonacijaEntity> donacije = repository.findById(id).get().getDonacije();
            donacije.stream()
                    .filter(e -> e.getKorisnik().equals(korisnikEntity) && !e.getArhivirana())
                    .collect(Collectors.toList());
            List<RezervacijaStavkaEntity> rezervacijaStavkaEntities = rezervacijaStavkaRepository.findAll();
            boolean flag = false;
            for (DonacijaEntity donacija : donacije) {
                if (rezervacijaStavkaEntities.stream().anyMatch(e -> !e.getRezervacija().getArhivirana() &&
                        e.getDonacijaStavka().getDonacija().getId().equals(donacija.getId()))) {
                    flag = true;
                    break;
                }
            }

            if (repository.existsById(id) && !flag) {

                for (ObavjestenjeEntity obavjestenje : repository.findById(id).get().getObavjestenja()) {
                    obavjestenjeService.delete(obavjestenje.getId());
                }
                List<DonacijaEntity> donations = repository.findById(id).get().getDonacije();
                donations.stream()
                        .filter(e -> e.getKorisnik().equals(korisnikEntity) && !e.getArhivirana())
                        .collect(Collectors.toList());
                for (DonacijaEntity donacija : donations) {
                    donacijaService.delete(donacija.getId());
                }
                korisnikEntity.setStatus(KorisnikEntity.Status.BLOCKED);
            } else {
                throw new Exception("Nije moguće obrisati nalog. Postoje donacije koje su rezervisane!");
            }
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void deleteUserByAdmin(Integer id) throws Exception {
        KorisnikEntity korisnik = repository.findById(id).get();
        korisnik.setStatus(KorisnikEntity.Status.BLOCKED);
        emailService.sendSimpleMailDeleted(korisnik.getEmail());
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws Exception {
        KorisnikEntity korisnikEntity = repository.findByEmail(email);
        korisnikEntity.setResetToken(token);
        repository.save(korisnikEntity);

    }

    @Override
    public void updatePassword(Integer id, ChangePasswordRequest request) {
        KorisnikEntity entity = repository.findById(id).get();
        if (request.getLozinka() != null && request.getLozinka().length() > 0 && !request.getLozinka().equals(entity.getLozinka())) {
            entity.setLozinka(passwordEncoder.encode(request.getLozinka()));
        }
        entity.setId(id);
        entity.setResetToken(null);
        repository.save(entity);

    }

}

package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.ForbiddenException;
import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.*;
import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.enums.Role;
import com.example.bldonate.models.requests.ChangePasswordRequest;
import com.example.bldonate.models.requests.ChangeRoleRequest;
import com.example.bldonate.models.requests.ChangeStatusRequest;
import com.example.bldonate.models.requests.UserUpdateRequest;
import com.example.bldonate.repositories.KorisnikRepository;
import com.example.bldonate.services.*;
import com.example.bldonate.util.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class KorisnikController {


    private final KorisnikService service;
    private final OglasService oglasService;
    private final DonacijaService donacijaService;
    @Value("${spring.mail.username}") private String sender;
    private final KorisnikRepository korisnikRepository;

    private final JavaMailSender javaMailSender;

    private final RezervacijaService rezervacijaService;

    private final ObavjestenjeService obavjestenjeService;

    public KorisnikController(KorisnikService service, OglasService oglasService, ObavjestenjeService obavjestenjeService, RezervacijaService rezervacijaService, DonacijaService donacijaService, JavaMailSender javaMailSender, KorisnikRepository korisnikRepository) {
        this.service = service;
        this.oglasService = oglasService;
        this.obavjestenjeService = obavjestenjeService;
        this.rezervacijaService = rezervacijaService;
        this.donacijaService = donacijaService;
        this.javaMailSender = javaMailSender;
        this.korisnikRepository = korisnikRepository;
    }

  /*  @GetMapping
    List<Korisnik> getAll()
    {
        return service.getAll();
    }
*/


    @GetMapping("/{id}")
    public KorisnikEntity findById(@PathVariable Integer id) throws NotFoundException {
        return service.findEntityById(id);
    }

    @PutMapping("/{id}")
    public Korisnik update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest request,Authentication auth) throws Exception {
        JwtUser user=(JwtUser)auth.getPrincipal();
        if(!user.getId().equals(id))
        {
            throw new ForbiddenException();
        }
        return service.update(id,request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) throws Exception {
        KorisnikEntity entity=service.findEntityById(id);
        if(entity.getRola().equals(Role.DONATOR)) {
            service.deleteDonor(id);
        }
        else if(entity.getRola().equals(Role.KORISNIK))
        {
            service.deleteUser(id);
        }
    }
/*
    @PutMapping("/{id}")
    public Korisnik update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest request, Authentication auth) {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        if (!jwtUser.getId().equals(id))
            throw new ForbiddenException();
        return service.update(id, request);
    }*/

 /*   @GetMapping("/donations")
    public List<Donacija> getAllDonacija()
    {
        return donacijaService.getAll();
    }*/

    @GetMapping("/{id}/donations")
    public List<Donacija> getAllDonacijaByDonorId(@PathVariable Integer id)
    {
        return donacijaService.getAllDonacijaByDonorId(id);
    }

    @GetMapping("/{id}/donations/archive")
    public List<Donacija> getAllArchiveDonations(@PathVariable Integer id)
    {
        return donacijaService.getAllArchiveDonations(id);
    }

    @GetMapping("/{id}/donations/archive/range")
    public List<Donacija> getAllArchiveDonationsRange(@PathVariable Integer id, Date pocetni, Date krajnji)
    {
        return  donacijaService.getAllArchiveRange(id,pocetni,krajnji);
    }

    @GetMapping("/{id}/ads")
    public List<Oglas> getAllAds(@PathVariable Integer id)
    {
        return oglasService.getAllOglasiById(id);
    }

    @GetMapping("/{id}/notifications")
    public List<Obavjestenje> getAllObavjestenje(@PathVariable Integer id)
    {
        return obavjestenjeService.getAllObavjestenjaKorisnik(id);
    }

    @GetMapping("/donors")
    public List<Korisnik> getAllDon()
    {
        return service.getAllDon();
    }

    @GetMapping("/{id}/donors")
    public List<Korisnik> getAllDonors(@PathVariable Integer id)
    {
       return service.getAllDonors(id);
    }

    @GetMapping("/{id}/donor/reservations")
    public List<Rezervacija> getAllRezervacijaForDonor(@PathVariable Integer id)
    {
        return service.getAllReservationsDonor(id);
    }
    @GetMapping("/{id}/donor/done_reservations")
    public List<Rezervacija> getAllDoneRezervacijaForDonor(@PathVariable Integer id)
    {
        return service.getAllDoneReservationsDonor(id);
    }

    @GetMapping("/{id}/reservations")
    public List<Rezervacija> getAllReservations(@PathVariable Integer id)
    {
        return rezervacijaService.getAllReservations(id);
    }

     @GetMapping("/{id}/reservations/archive")
    public List<Rezervacija> getAllArchiveReservations(@PathVariable Integer id)
     {
         return rezervacijaService.getAllArchiveReservations(id);
     }

     @GetMapping("/{id}/reservations/archive/range")
     public List<Rezervacija> getAllArchiveRange(@PathVariable Integer id, Date pocetni, Date krajnji)
     {
         return  rezervacijaService.getAllArchiveRange(id,pocetni,krajnji);
     }

     @GetMapping("/users")
     public List<Korisnik> getAllUsers()
     {
         return  service.getAll();
     }


     @GetMapping("/requests")
     public List<Korisnik> getAllRequests()
     {
         return service.getAllUnapprovedUsers();
     }


    @DeleteMapping("/{id}/deleteUser")
    public void deleteByAdmin(@PathVariable Integer id) throws Exception {
        service.deleteUserByAdmin(id);
    }

    @PatchMapping("/{id}/status")
    public void changeStatus(@PathVariable Integer id, @RequestBody @Valid ChangeStatusRequest request, Authentication auth) throws Exception {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        if (jwtUser.getId().equals(id))
            throw new ForbiddenException();
        service.changeStatus(id, request);
    }

    @PatchMapping("/{id}/role")
    public void changeRole(@PathVariable Integer id, @RequestBody @Valid ChangeRoleRequest request, Authentication auth) {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        if (jwtUser.getId().equals(id))
            throw new ForbiddenException();
        service.changeRole(id, request);
    }



    @PostMapping("/{id}/reset_password")
    public void processResetPassword( @PathVariable Integer id, @Valid @RequestBody  ChangePasswordRequest request) throws Exception {
       service.updatePassword(id,request);
    }

}


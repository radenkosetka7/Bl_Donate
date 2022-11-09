package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.ForbiddenException;
import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.*;
import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.requests.ChangeRoleRequest;
import com.example.bldonate.models.requests.ChangeStatusRequest;
import com.example.bldonate.models.requests.UserUpdateRequest;
import com.example.bldonate.services.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class KorisnikController {


    private final Class<Korisnik> korisnikClass;
    private final KorisnikService service;
    private final OglasService oglasService;
    private final DonacijaService donacijaService;

    private final RezervacijaService rezervacijaService;

    private final ObavjestenjeService obavjestenjeService;

    public KorisnikController(KorisnikService service, OglasService oglasService, ObavjestenjeService obavjestenjeService, RezervacijaService rezervacijaService, DonacijaService donacijaService,  Class<Korisnik> korisnikClass) {
        this.service = service;
        this.oglasService = oglasService;
        this.obavjestenjeService = obavjestenjeService;
        this.rezervacijaService = rezervacijaService;
        this.donacijaService = donacijaService;
        this.korisnikClass = korisnikClass;
    }

  /*  @GetMapping
    List<Korisnik> getAll()
    {
        return service.getAll();
    }
*/

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Korisnik insert(@RequestBody KorisnikEntity request) throws NotFoundException {
        return service.insert(request,korisnikClass);
    }


    @GetMapping("/{id}")
    public KorisnikEntity findById(@PathVariable Integer id) throws NotFoundException {
        return service.findEntityById(id);
    }

/*    @PutMapping("/{id}")
    public Korisnik update(@PathVariable Integer id, @RequestBody KorisnikEntity request) throws NotFoundException {
        return service.update(id,request,korisnikClass);
    }


    @PutMapping("/{id}")
    public Korisnik update(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest request, Authentication auth) {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        if (!jwtUser.getId().equals(id))
            throw new ForbiddenException();
        return service.update(id, request);
    }*/

    @GetMapping("/donations")
    public List<Donacija> getAllDonacija()
    {
        return donacijaService.getAll();
    }

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

   /* @GetMapping("/donors")
    public List<Korisnik> getAllDonors()
    {
       return service.getAllDonors();
    }
*/
    @GetMapping("/{id}/donor/reservations")
    public List<Rezervacija> getAllRezervacijaForDonor(@PathVariable Integer id)
    {
        return service.getAllReservationsDonor(id);
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

     /*@GetMapping("/users")
     public List<Korisnik> getAllUsers()
     {
         return  service.getAll();
     }

     @GetMapping("/requests")
     public List<Korisnik> getAllRequests()
     {
         return service.getAllUnapprovedUsers();
     }*/

    @DeleteMapping("/{id}")
    public void deleteDonor(@PathVariable Integer id) throws Exception {
        service.deleteDonor(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUsers(@PathVariable Integer id) throws Exception {
        service.deleteUser(id);
    }

    @DeleteMapping
    public void deleteByAdmin(Integer id) throws Exception {
        service.deleteUserByAdmin(id);
    }

   /* @PatchMapping("/{id}/status")
    public void changeStatus(@PathVariable Integer id, @RequestBody @Valid ChangeStatusRequest request, Authentication auth) {
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
*/

}

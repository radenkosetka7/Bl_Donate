package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.*;
import com.example.bldonate.models.requests.KorisnikRequest;
import com.example.bldonate.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/users")
public class KorisnikController {

    private final KorisnikService service;
    private final PravnoLiceService pravnoLiceService;
    private final FizickoLiceService fizickoLiceService;
    private final OglasService oglasService;

    private final RezervacijaService rezervacijaService;

    private final ObavjestenjeService obavjestenjeService;

    public KorisnikController(KorisnikService service, PravnoLiceService pravnoLiceService, FizickoLiceService fizickoLiceService, OglasService oglasService, ObavjestenjeService obavjestenjeService, RezervacijaService rezervacijaService) {
        this.service = service;
        this.pravnoLiceService = pravnoLiceService;
        this.fizickoLiceService = fizickoLiceService;
        this.oglasService = oglasService;
        this.obavjestenjeService = obavjestenjeService;
        this.rezervacijaService = rezervacijaService;
    }

    @GetMapping
    List<Korisnik> getAll()
    {
        return service.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Korisnik insert(@RequestBody KorisnikRequest request) throws NotFoundException {
        return service.insert(request);
    }

    @GetMapping("/{id}")
    public Korisnik findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public Korisnik update(@PathVariable Integer id, @RequestBody KorisnikRequest request) throws NotFoundException {
        return service.update(id,request);
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
    public List<Object> getAllDonors()
    {
        return Stream.concat(pravnoLiceService.getAll().stream(),fizickoLiceService.getAll().stream()).collect(Collectors.toList());
    }



    @GetMapping("/{id}/reservations")
    public List<Rezervacija> getAllReservations(@PathVariable Integer id)
    {
        return rezervacijaService.getAllReservations(id);
    }

     @GetMapping("/{id}/archive")
    public List<Rezervacija> getAllArchiveReservations(@PathVariable Integer id)
     {
         return rezervacijaService.getAllArchiveReservations(id);
     }

     @GetMapping("/{id}/range")
     public List<Rezervacija> getAllArchiveRange(@PathVariable Integer id, Date pocetni, Date krajnji)
     {
         return  rezervacijaService.getAllArchiveRange(id,pocetni,krajnji);
     }

}

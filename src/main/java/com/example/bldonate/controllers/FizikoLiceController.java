package com.example.bldonate.controllers;


import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.*;
import com.example.bldonate.models.requests.DonacijaRequest;
import com.example.bldonate.models.requests.FizickoLiceRequest;
import com.example.bldonate.models.requests.PravnoLiceRequest;
import com.example.bldonate.services.DonacijaService;
import com.example.bldonate.services.FizickoLiceService;
import com.example.bldonate.services.ObavjestenjeService;
import com.example.bldonate.services.OglasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/individuals")
public class FizikoLiceController {

    private final FizickoLiceService service;
    private final ObavjestenjeService obavjestenjeService;
    private final OglasService oglasService;
    private final DonacijaService donacijaService;

    public FizikoLiceController(FizickoLiceService service, OglasService oglasService, ObavjestenjeService obavjestenjeService, DonacijaService donacijaService) {
        this.service = service;
        this.oglasService = oglasService;
        this.obavjestenjeService = obavjestenjeService;
        this.donacijaService = donacijaService;
    }

    @GetMapping
    public List<FizickoLice> findAll()
    {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FizickoLice findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FizickoLice insert(@RequestBody FizickoLiceRequest request) throws NotFoundException {
        return service.insert(request);
    }

    @PutMapping("/{id}")
    public FizickoLice update(@PathVariable Integer id,@RequestBody FizickoLiceRequest request) throws NotFoundException {
        return service.update(id,request);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws Exception {
        service.delete(id);
    }


    @GetMapping("/ads")
    public List<Oglas> findAllOglas()
    {
        return oglasService.getAll();
    }


    @GetMapping("/{id}/notifications")
    public List<Obavjestenje> getAllObavjestenje(@PathVariable Integer id)
    {
        return obavjestenjeService.getAllObavjestenjaDonator(id);
    }

    @GetMapping("/{id}/donations")
    public List<Donacija> getAllDonacijaByDonorId(@PathVariable Integer id)
    {
        return donacijaService.getAllDonacijaByDonorId(id);
    }

    @GetMapping("/{id}/archive")
    public List<Donacija> getAllArchiveDonations(@PathVariable Integer id)
    {
        return donacijaService.getAllArchiveDonations(id);
    }

    @GetMapping("/{id}/range")
    public List<Donacija> getAllArchiveRange(@PathVariable Integer id, Date pocetni, Date krajnji)
    {
        return  donacijaService.getAllArchiveRange(id,pocetni,krajnji);
    }

    @GetMapping("/{id}/reservations")
    public List<Rezervacija> getAllReservations(@PathVariable Integer id)
    {
        return service.getAllReservations(id);
    }
}

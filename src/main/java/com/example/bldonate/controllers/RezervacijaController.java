package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Rezervacija;
import com.example.bldonate.models.requests.RezervacijaRequest;
import com.example.bldonate.services.RezervacijaService;
import com.example.bldonate.services.RezervacijaStavkaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class RezervacijaController {

   private final RezervacijaService service;

    private final RezervacijaStavkaService rezervacijaStavkaService;

    public RezervacijaController(RezervacijaService service, RezervacijaStavkaService rezervacijaStavkaService) {
        this.service = service;
        this.rezervacijaStavkaService = rezervacijaStavkaService;
    }

    @GetMapping("/active/{id}")
    public List<Rezervacija> findAll(@PathVariable Integer id)
    {
        return service.getAll(id);
    }

    @GetMapping("/{id}")
    public Rezervacija findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rezervacija insert(@RequestBody RezervacijaRequest rezervacija) throws NotFoundException {
        return service.insert(rezervacija);
    }

    @PutMapping("/{id}")
    public Rezervacija update(@PathVariable Integer id,@RequestBody RezervacijaRequest request) throws NotFoundException {
        return service.update(id,request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable  Integer id) throws NotFoundException {
        service.delete(id);
    }
}

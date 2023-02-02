package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.RezervacijaStavka;
import com.example.bldonate.models.requests.RezervacijaStavkaRequest;
import com.example.bldonate.services.RezervacijaStavkaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/res_items")
public class RezervacijaStavkaController {

    private final RezervacijaStavkaService service;

    public RezervacijaStavkaController(RezervacijaStavkaService service) {
        this.service = service;
    }

    @GetMapping
    public List<RezervacijaStavka> findAll()
    {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public RezervacijaStavka findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RezervacijaStavka insert(@RequestBody RezervacijaStavkaRequest request) throws NotFoundException {
        return service.insert(request);
    }

    @PutMapping("/{id}")
    public RezervacijaStavka update(@PathVariable Integer id, @RequestBody RezervacijaStavkaRequest request) throws NotFoundException {
        return service.update(id,request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        service.delete(id);
    }
}

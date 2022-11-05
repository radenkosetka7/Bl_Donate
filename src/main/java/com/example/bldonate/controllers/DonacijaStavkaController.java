package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.DonacijaStavka;

import com.example.bldonate.models.requests.DonacijaStavkaRequest;;
import com.example.bldonate.services.DonacijaStavkaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/don_items")
public class DonacijaStavkaController {

    private final DonacijaStavkaService service;

    public DonacijaStavkaController(DonacijaStavkaService service) {
        this.service = service;
    }

    @GetMapping
    public List<DonacijaStavka> findAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public DonacijaStavka findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonacijaStavka insert(@RequestBody DonacijaStavkaRequest request) throws NotFoundException {
        return service.insert(request);
    }

    @PutMapping("/{id}")
    public DonacijaStavka update(@PathVariable Integer id, @RequestBody DonacijaStavkaRequest request) throws NotFoundException {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws Exception {
        service.delete(id);

    }
}

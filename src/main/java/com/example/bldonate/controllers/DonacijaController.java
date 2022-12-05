package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Donacija;
import com.example.bldonate.models.requests.DonacijaRequest;
import com.example.bldonate.services.DonacijaService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonacijaController {

   private final DonacijaService service;

    public DonacijaController(DonacijaService service) {
        this.service = service;
    }

    @GetMapping("/active/{id}")
    public List<Donacija> findAll(@PathVariable Integer id)
    {
       return service.getAll(id);
    }

    @GetMapping("/{id}")
    public Donacija findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Donacija insert(@RequestBody DonacijaRequest request) throws NotFoundException {
        return service.insert(request);
    }

    @PutMapping("/{id}")
    public Donacija update(@PathVariable Integer id,@RequestBody DonacijaRequest request) throws NotFoundException {
        return service.update(id,request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws Exception {
        service.delete(id);
    }


}

package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Donacija;
import com.example.bldonate.models.dto.Donator;
import com.example.bldonate.models.entities.DonatorEntity;
import com.example.bldonate.models.requests.DonacijaRequest;
import com.example.bldonate.models.requests.DonatorRequest;
import com.example.bldonate.services.DonatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/donors")
public class DonatorController {

    private final DonatorService service;

    public DonatorController(DonatorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DonatorEntity insert(@RequestBody  DonatorRequest request) throws NotFoundException {
        return service.insert(request);
    }

    @GetMapping("/{id}")
    public Donator findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public DonatorEntity update(@PathVariable Integer id, DonatorRequest request) throws NotFoundException {
        return service.update(id,request);
    }
    /*
    @GetMapping
    public List<Donator> findAll()
    {
        return service.getAll();
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        service.delete(id);
    }*/
}

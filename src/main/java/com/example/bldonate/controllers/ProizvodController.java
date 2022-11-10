package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Proizvod;
import com.example.bldonate.models.requests.ProizvodRequest;
import com.example.bldonate.services.ProizvodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProizvodController {

    private final ProizvodService service;

    public ProizvodController(ProizvodService service) {
        this.service = service;
    }

    @GetMapping
    public List<Proizvod> getAll()
    {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Proizvod findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        service.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proizvod insert(@RequestBody ProizvodRequest request) throws Exception{
        return service.insert(request);
    }



    @PutMapping("/{id}")
    public Proizvod update(@PathVariable Integer id, @RequestBody ProizvodRequest request) throws Exception {
        return service.update(id,request);
    }



}

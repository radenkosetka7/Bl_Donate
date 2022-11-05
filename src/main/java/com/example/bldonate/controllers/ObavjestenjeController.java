package com.example.bldonate.controllers;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.FizickoLice;
import com.example.bldonate.models.dto.Obavjestenje;
import com.example.bldonate.models.requests.FizickoLiceRequest;
import com.example.bldonate.models.requests.ObavjestenjeRequest;
import com.example.bldonate.services.ObavjestenjeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/notifications")
@RestController
public class ObavjestenjeController {

    private final ObavjestenjeService service;

    public ObavjestenjeController(ObavjestenjeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Obavjestenje> findAll()
    {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Obavjestenje findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Obavjestenje insert(@RequestBody ObavjestenjeRequest request) throws NotFoundException {
        return service.insert(request);
    }

    @PutMapping("/{id}")
    public Obavjestenje update(@PathVariable Integer id,@RequestBody ObavjestenjeRequest request) throws NotFoundException {
        return service.update(id,request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        service.delete(id);
    }
}

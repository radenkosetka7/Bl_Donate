package com.example.bldonate.controllers;


import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Oglas;
import com.example.bldonate.models.requests.OglasRequest;
import com.example.bldonate.services.OglasService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class OglasController {

    private final OglasService service;

    public OglasController(OglasService service) {
        this.service = service;
    }



    @GetMapping
    List<Oglas> getAll()
    {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Oglas findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) throws NotFoundException {
        service.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Oglas insert(@RequestBody OglasRequest request) throws NotFoundException {
        System.out.println("TEST " + request.getKorisnikId());
        System.out.println("sadrzaj " + request.getSadrzaj());
        return service.insert(request);
    }


    @PutMapping("/{id}")
    public Oglas update(@PathVariable Integer id,@RequestBody OglasRequest request) throws NotFoundException {
        return service.update(id,request);
    }

}

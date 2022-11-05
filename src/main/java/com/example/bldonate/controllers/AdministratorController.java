package com.example.bldonate.controllers;


import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.*;
import com.example.bldonate.models.requests.AdministratorRequest;
import com.example.bldonate.services.AdministratorService;
import com.example.bldonate.services.FizickoLiceService;
import com.example.bldonate.services.KorisnikService;
import com.example.bldonate.services.PravnoLiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/admins")
public class AdministratorController {


    private final AdministratorService service;
    private final KorisnikService korisnikService;
    private final FizickoLiceService fizickoLiceService;
    private final PravnoLiceService pravnoLiceService;

    public AdministratorController(AdministratorService service, KorisnikService korisnikService, FizickoLiceService fizickoLiceService, PravnoLiceService pravnoLiceService) {
        this.service = service;
        this.korisnikService = korisnikService;
        this.fizickoLiceService = fizickoLiceService;
        this.pravnoLiceService = pravnoLiceService;
    }



    @GetMapping("/{id}")
    public Administrator findById(@PathVariable Integer id) throws NotFoundException {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Administrator insert(@RequestBody AdministratorRequest request) throws NotFoundException, NoSuchAlgorithmException {
        return service.insert(request);
    }


    @GetMapping("/users")
    public List<Korisnik> getAllUsers()
    {
        return korisnikService.getAll();
    }

    @GetMapping("/donors")
    public List<Object> getAllDonors()
    {
        return Stream.concat(fizickoLiceService.getAll().stream()
                ,pravnoLiceService.getAll().stream()).collect(Collectors.toList());
    }


    @GetMapping("/requests/donors")
    public List<Object> getAllUnapprovedDonors()
    {
        List<FizickoLice> fizickaLica=fizickoLiceService.getAllUnapprovedUsers();
        List<PravnoLice> pravnaLica=pravnoLiceService.getAllUnapprovedUsers();
        return Stream.concat(fizickoLiceService.getAllUnapprovedUsers().stream()
                ,pravnoLiceService.getAllUnapprovedUsers().stream()).collect(Collectors.toList());
    }

    @GetMapping("/requests/users")
    public List<Korisnik> getAllUnapprovedUsers()
    {
        return korisnikService.getAllUnapprovedUsers();
    }

    @DeleteMapping("/users")
    public void deleteUser(Integer id) throws Exception {
        service.deleteUser(id);
    }

    @DeleteMapping("/donors")
    public void deleteDonor(Integer id) throws Exception {
        service.deleteDonor(id);
    }

}

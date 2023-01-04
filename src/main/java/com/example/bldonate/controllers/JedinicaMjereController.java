package com.example.bldonate.controllers;

import com.example.bldonate.models.dto.JedinicaMjere;
import com.example.bldonate.models.entities.JedinicaMjereEntity;
import com.example.bldonate.models.entities.KategorijaProizvodaEntity;
import com.example.bldonate.services.JedinicaMjereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/units")
public class JedinicaMjereController {

    private final JedinicaMjereService jedinicaMjereService;

    public JedinicaMjereController(JedinicaMjereService jedinicaMjereService) {
        this.jedinicaMjereService = jedinicaMjereService;
    }

    @GetMapping
    List<JedinicaMjere> getAll()
    {
        return jedinicaMjereService.getAll();
    }
}

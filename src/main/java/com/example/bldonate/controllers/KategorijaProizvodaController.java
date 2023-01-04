package com.example.bldonate.controllers;

import com.example.bldonate.models.dto.KategorijaProizvoda;
import com.example.bldonate.models.dto.Oglas;
import com.example.bldonate.models.entities.KategorijaProizvodaEntity;
import com.example.bldonate.services.KategorijaProizvodaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class KategorijaProizvodaController {

    private final KategorijaProizvodaService kategorijaProizvodaService;

    public KategorijaProizvodaController(KategorijaProizvodaService kategorijaProizvodaService) {
        this.kategorijaProizvodaService = kategorijaProizvodaService;
    }

    @GetMapping
    List<KategorijaProizvoda> getAll()
    {
        return kategorijaProizvodaService.getAll();
    }
}

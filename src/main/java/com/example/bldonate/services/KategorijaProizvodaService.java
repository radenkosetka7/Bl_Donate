package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.KategorijaProizvoda;
import com.example.bldonate.models.entities.KategorijaProizvodaEntity;

import java.util.List;

public interface KategorijaProizvodaService {

  KategorijaProizvodaEntity findById(Integer id) throws NotFoundException;
  List<KategorijaProizvoda> getAll();
}

package com.example.bldonate.repositories;

import com.example.bldonate.models.entities.ObavjestenjeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ObavjestenjeRepository extends JpaRepository<ObavjestenjeEntity,Integer> {

    @Query("SELECT o from ObavjestenjeEntity o where o.korisnik.id=:id")
    List<ObavjestenjeEntity> getAllObavjestenjaKorisnik(Integer id);

    @Query("SELECT o from ObavjestenjeEntity o where o.donator.id=:id")
    List<ObavjestenjeEntity> getAllObavjestenjaDonator(Integer id);
}

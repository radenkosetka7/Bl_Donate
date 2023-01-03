package com.example.bldonate.repositories;

import com.example.bldonate.models.entities.RezervacijaEntity;
import com.example.bldonate.models.entities.RezervacijaStavkaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RezervacijaStavkaRepository extends JpaRepository<RezervacijaStavkaEntity,Integer> {


    @Query("SELECT r from RezervacijaStavkaEntity r where r.donacijaStavka.donacija.korisnik.id=:id and r.donacijaStavka.donacija.arhivirana=false")
    List<RezervacijaStavkaEntity> getAllReservationsByDonor(Integer id);
}

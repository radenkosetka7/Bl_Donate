package com.example.bldonate.repositories;

import com.example.bldonate.models.entities.RezervacijaStavkaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RezervacijaStavkaRepository extends JpaRepository<RezervacijaStavkaEntity,Integer> {

  //  @Query("SELECT r from RezervacijaStavkaEntity r where r.donacijaStavka.korisnik.id=:id and r.donacijaStavka.donacija.arhivirana=false")
    //List<RezervacijaStavkaEntity> getAllReservationsByKorisnik(Integer id);

    @Query("SELECT r from RezervacijaStavkaEntity r where r.donacijaStavka.donacija.korisnik.id=:id and r.donacijaStavka.donacija.arhivirana=false")
    List<RezervacijaStavkaEntity> getAllReservationsByDonor(Integer id);
    @Query("SELECT r from RezervacijaStavkaEntity r where r.donacijaStavka.donacija.korisnik.id=:id")
    List<RezervacijaStavkaEntity> getAllDoneReservationsByDonor(Integer id);
}

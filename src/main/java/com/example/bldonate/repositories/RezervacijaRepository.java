package com.example.bldonate.repositories;

import com.example.bldonate.models.entities.RezervacijaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface RezervacijaRepository extends JpaRepository<RezervacijaEntity,Integer> {

    @Query("SELECT r from RezervacijaEntity r where r.korisnik.id=:id")
    List<RezervacijaEntity> getAllReservations(Integer id);

    @Query("SELECT r from RezervacijaEntity r where r.arhivirana=:arhiva and r.korisnik.id=:id")
    List<RezervacijaEntity> getAllReservationByArchiveFlag(Boolean arhiva,Integer id);


    @Query("SELECT r from RezervacijaEntity r where r.arhivirana=true and r.korisnik.id=:id")
    List<RezervacijaEntity> getAllArchiveReservations(Integer id);

    @Query("SELECT r from RezervacijaEntity r " +
            "where r.korisnik.id=:id and (r.datumRezervacije between :pocetniDatum and :krajnjiDatum)")
    List<RezervacijaEntity> getAllArchiveDateRange(Integer id,Date pocetniDatum, Date krajnjiDatum);

}


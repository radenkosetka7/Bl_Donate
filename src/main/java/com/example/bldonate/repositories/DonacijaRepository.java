package com.example.bldonate.repositories;


import com.example.bldonate.models.entities.DonacijaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


public interface DonacijaRepository extends JpaRepository<DonacijaEntity,Integer> {

   @Query("SELECT d from DonacijaEntity d where d.korisnik.id=:id")
   List<DonacijaEntity> getAllDonacijaByDonorId(Integer id);

   @Query("SELECT d from DonacijaEntity d where d.arhivirana=:arhiva and d.korisnik.id=:id")
   List<DonacijaEntity> getAllDonacijeByArhiviranaFlag(Boolean arhiva,Integer id);

   @Query("SELECT d from DonacijaEntity d where d.arhivirana=:arhiva")
   List<DonacijaEntity> get(Boolean arhiva);

   @Query("SELECT d from DonacijaEntity d where d.arhivirana=true and d.korisnik.id=:id")
   List<DonacijaEntity> getAllArchiveDonations(Integer id);

   @Query("SELECT d from DonacijaEntity d " +
           "where d.korisnik.id=:id and (d.datumDoniranja between :pocetniDatum and :krajnjiDatum)")
   List<DonacijaEntity> getAllArchiveDateRange(Integer id, Date pocetniDatum, Date krajnjiDatum);


}

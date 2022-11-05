package com.example.bldonate.repositories;


import com.example.bldonate.models.entities.KorisnikEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KorisnikRepository extends JpaRepository<KorisnikEntity,Integer> {


    @Query("SELECT k from KorisnikEntity k where k.odoboren=false")
    List<KorisnikEntity> getAllUnapprovedUsers();

    @Query("SELECT k from KorisnikEntity k where k.odoboren=true")
    List<KorisnikEntity> getAllApprovedUsers();


}

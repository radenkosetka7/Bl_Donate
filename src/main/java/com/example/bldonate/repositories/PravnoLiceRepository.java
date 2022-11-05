package com.example.bldonate.repositories;


import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.entities.PravnoLiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PravnoLiceRepository extends JpaRepository<PravnoLiceEntity,Integer> {


    @Query("SELECT p from PravnoLiceEntity p where p.donator.odoboren=false")
    List<PravnoLiceEntity> getAllUnapprovedUsers();


    @Query("SELECT f from FizickoLiceEntity f where f.donator.odoboren=true")
    List<PravnoLiceEntity> getAllApprovedUsers();



}

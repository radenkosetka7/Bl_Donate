package com.example.bldonate.repositories;


import com.example.bldonate.models.entities.DonacijaEntity;
import com.example.bldonate.models.entities.FizickoLiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FizickoLiceRepository extends JpaRepository<FizickoLiceEntity,Integer> {

    @Query("SELECT f from FizickoLiceEntity f where f.donator.odoboren=false")
    List<FizickoLiceEntity> getAllUnApprovedUsers();

    @Query("SELECT f from FizickoLiceEntity f where f.donator.odoboren=true ")
    List<FizickoLiceEntity> getAllApprovedUsers();

}

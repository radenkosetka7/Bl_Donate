package com.example.bldonate.repositories;

import com.example.bldonate.models.entities.DonacijaEntity;
import com.example.bldonate.models.entities.DonacijaStavkaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface DonacijaStavkaRepository extends JpaRepository<DonacijaStavkaEntity,Integer> {


}

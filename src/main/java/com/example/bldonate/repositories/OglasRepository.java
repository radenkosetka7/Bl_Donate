package com.example.bldonate.repositories;


import com.example.bldonate.models.entities.ObavjestenjeEntity;
import com.example.bldonate.models.entities.OglasEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OglasRepository extends JpaRepository<OglasEntity,Integer> {

   @Query("SELECT o from OglasEntity o where o.korisnik.id=:id")
    List<OglasEntity> getAllOglasiById(Integer id);
}

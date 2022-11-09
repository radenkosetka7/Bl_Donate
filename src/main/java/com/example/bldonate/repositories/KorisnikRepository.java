package com.example.bldonate.repositories;


import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<KorisnikEntity,Integer> {


/*
  */
/*  @Query("SELECT k from KorisnikEntity k where k.status=:status and (k.rola=:rola or k.rola=:rola1)")
    List<KorisnikEntity> getAllUnapprovedUsers(KorisnikEntity.Status status, Role rola,Role rola1);*//*

   // List<KorisnikEntity> findByRolaAndStatus(KorisnikEntity.Status status,Role rola);

   // @Query(value = "SELECT k from KorisnikEntity k where k.status=:staus and (k.rola=:rola or k.rola=:rola1)")
    List<KorisnikEntity> getAllByRolaOrRolaAndStatus (KorisnikEntity.Status status,Role rola,Role rola1);


    //@Query("SELECT k from KorisnikEntity k where k.status=KorisnikEntity.Status.ACTIVE and k.rola= :rola")
    List<KorisnikEntity> getAllByRolaAndStatus(KorisnikEntity.Status status,Role rola);
*/



    Boolean existsByKorisnickoIme(String username);

    Boolean existsByKorisnickoImeAndIdNot(String username, Integer id);

    Optional<KorisnikEntity> findByKorisnickoImeAndStatus(String username,KorisnikEntity.Status status);

}

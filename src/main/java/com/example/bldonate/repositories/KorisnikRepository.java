package com.example.bldonate.repositories;


import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.enums.Role;
import com.example.bldonate.models.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<KorisnikEntity,Integer> {


/*  @Query("SELECT k from KorisnikEntity k where k.status=:status and (k.rola=:rola or k.rola=:rola1)")
    List<KorisnikEntity> getAllUnapprovedUsers(KorisnikEntity.Status status, Role rola,Role rola1);*//*

   // List<KorisnikEntity> findByRolaAndStatus(KorisnikEntity.Status status,Role rola);
   */

    List<KorisnikEntity> getAllByStatusAndRolaOrRola (KorisnikEntity.Status status, Role rola,Role rola1);


   List<KorisnikEntity> getAllByStatusAndRola(KorisnikEntity.Status status,Role rola);

    List<KorisnikEntity> getAllByStatus(KorisnikEntity.Status status);

    KorisnikEntity findByEmail(String mail);

   KorisnikEntity findByResetToken(String token);

    Boolean existsByKorisnickoIme(String username);

    Boolean existsByKorisnickoImeAndIdNot(String username, Integer id);

    Optional<KorisnikEntity> findByKorisnickoImeAndStatus(String username,KorisnikEntity.Status status);

}

package com.example.bldonate.repositories;


import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.enums.Role;
import com.example.bldonate.models.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<KorisnikEntity,Integer> {


    @Query("SELECT k from KorisnikEntity k where k.status=:status and (k.rola=:rola or k.rola=:rola1)")
    List<KorisnikEntity> getAllUnapprovedUsers(KorisnikEntity.Status status, Role rola,Role rola1);

    @Query("SELECT k from KorisnikEntity k where k.status=:staus and (k.rola=:rola or k.rola=:rola1)")
    List<KorisnikEntity> getAllApprovedUsers(KorisnikEntity.Status status,Role rola,Role rola1);

    @Query("SELECT k from KorisnikEntity k where k.status=:staus and k.rola=:rola")
    List<KorisnikEntity> getAllDonors(KorisnikEntity.Status status,Role rola);



    Optional<KorisnikEntity> findByUsernameAndStatus(String username, KorisnikEntity.Status status);

    Boolean existsByUsername(String username);

    Boolean existsByUsernameAndIdNot(String username, Integer id);

}

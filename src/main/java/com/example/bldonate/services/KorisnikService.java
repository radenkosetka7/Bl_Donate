package com.example.bldonate.services;

import com.example.bldonate.exceptions.NotFoundException;
import com.example.bldonate.models.dto.Korisnik;
import com.example.bldonate.models.dto.LoginResponse;
import com.example.bldonate.models.dto.Rezervacija;
import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.requests.*;
import org.springframework.stereotype.Service;

import java.util.List;

public interface KorisnikService {
    //List<Korisnik> getAll();

    //LoginResponse findById(Integer id, Class<LoginResponse> response) throws NotFoundException;

    //List<Korisnik> getAllDonors();

    Korisnik  insert(KorisnikEntity korisnikEntity,Class<Korisnik> korisnik) throws NotFoundException;

    //Korisnik update(Integer id, KorisnikEntity korisnikEntity, Class<Korisnik> korisnik) throws NotFoundException;

    KorisnikEntity findEntityById(Integer id);


   // List<Korisnik> getAllUnapprovedUsers();

    /*void signUp(SignUpRequest request);

    void changeStatus(Integer userId, ChangeStatusRequest request);

    void changeRole(Integer userId, ChangeRoleRequest request);*/

   // Korisnik update(Integer id, UserUpdateRequest user);

    List<Rezervacija> getAllReservationsDonor(Integer id);

    void deleteUser(Integer id) throws Exception;

    void deleteDonor(Integer id) throws Exception;

    void deleteUserByAdmin(Integer id) throws Exception;
}


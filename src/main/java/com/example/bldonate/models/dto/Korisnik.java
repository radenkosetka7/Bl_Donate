package com.example.bldonate.models.dto;

import com.example.bldonate.models.enums.Role;
import com.example.bldonate.models.enums.UserStatus;
import lombok.Data;

import java.util.List;


@Data
public class Korisnik {

    private Integer id;
    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String email;
    private String brojTelefona;
    private String adresa;
    private String prezime;
    private byte[] logo;
    private String jmbg;
    private String pib;
    private Role rola;
    private UserStatus status;
}

package com.example.bldonate.models.dto;

import lombok.Data;

import java.util.List;


@Data
public class Korisnik {

    private Integer id;
    private String naziv;
    private String adresa;
    private String email;
    private String brojTelefona;
    private Boolean odoboren;
    private String korisnickoIme;
    private String lozinka;
    private byte[] logo;
}

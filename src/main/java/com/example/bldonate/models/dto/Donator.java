package com.example.bldonate.models.dto;


import lombok.Data;


@Data
public class Donator {

    private Integer id;
    private String korisnickoIme;
    private String lozinka;
    private String email;
    private String brojTelefona;
    private String adresa;
    private String ime;
    private Boolean odoboren;
    private byte[] logo;
}

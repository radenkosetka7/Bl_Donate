package com.example.bldonate.models.dto;

import lombok.Data;


@Data
public class Administrator {

    private Integer id;
    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;
    private String email;
    private String brojTelefona;

}

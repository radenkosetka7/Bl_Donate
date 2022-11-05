package com.example.bldonate.models.requests;

import lombok.Data;

@Data
public class AdministratorRequest {

    private String korisnickoIme;
    private String lozinka;
    private String ime;
    private String prezime;
    private String email;
    private String brojTelefona;
}

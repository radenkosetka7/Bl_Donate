package com.example.bldonate.models.requests;

import lombok.Data;

@Data
public class DonatorRequest {

    private String korisnickoIme;
    private String lozinka;
    private String email;
    private String brojTelefona;
    private String adresa;
    private String ime;
    private Boolean odoboren=false;
    private byte[] logo;
}

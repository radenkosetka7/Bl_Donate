package com.example.bldonate.models.requests;

import lombok.Data;

@Data
public class KorisnikRequest {

    private String naziv;
    private String adresa;
    private String email;
    private String brojTelefona;
    private Boolean odoboren=false;
    private String korisnickoIme;
    private String lozinka;
    private byte[] logo;
}

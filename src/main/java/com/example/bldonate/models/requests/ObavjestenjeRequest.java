package com.example.bldonate.models.requests;

import lombok.Data;

@Data
public class ObavjestenjeRequest {

    private String sadrzaj;
    private Boolean procitano;
    private Integer korisnik;
}

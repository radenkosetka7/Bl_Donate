package com.example.bldonate.models.dto;

import lombok.Data;

import java.sql.Date;
@Data
public class Oglas {

    private Integer id;
    private Date datumObjave;
    private String sadrzaj;
    private Boolean prevoz;
    private Boolean namirnice;
    private Korisnik korisnik;
}

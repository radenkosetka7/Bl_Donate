package com.example.bldonate.models.dto;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Donacija {

    private Integer id;
    private Date terminPreuzimanja;
    private String adresa;
    private String brojTelefona;
    private String napomena;
    private String donator;
    private Boolean prevoz;
    private Boolean arhivirana;
    private Date datumDoniranja;
    private List<DonacijaStavka> stavke;
}

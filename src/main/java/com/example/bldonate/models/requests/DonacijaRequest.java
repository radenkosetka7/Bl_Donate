package com.example.bldonate.models.requests;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
public class DonacijaRequest {

    private Integer id;
    private Date terminPreuzimanja;
    private String adresa;
    private String brojTelefona;
    private String napomena;
    private Integer donatorName;
    private Boolean prevoz;
    private Boolean arhivirana;
    private Date datumDoniranja;

}

package com.example.bldonate.models.requests;

import lombok.Data;

import java.util.Date;


@Data
public class RezervacijaRequest {

    private Date datumRezervacije;
    private Boolean arhivirana;
    private Integer korisnikId;
}

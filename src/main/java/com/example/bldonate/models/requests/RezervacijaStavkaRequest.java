package com.example.bldonate.models.requests;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RezervacijaStavkaRequest {

    private BigDecimal kolicina;
    private Integer rezervacijaId;
    private Integer donacijaStavkaId;

}

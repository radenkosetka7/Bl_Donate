package com.example.bldonate.models.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class RezervacijaStavka {

    private Integer id;
    private BigDecimal kolicina;
    private Proizvod proizvod;
    private Donacija donacija;

}

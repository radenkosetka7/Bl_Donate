package com.example.bldonate.models.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class DonacijaStavka {

    private BigDecimal kolicina;
    private Integer id;
    private Proizvod proizvodId;
}

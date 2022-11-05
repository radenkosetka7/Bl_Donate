package com.example.bldonate.models.requests;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class DonacijaStavkaRequest {

    private BigDecimal kolicina;
    private Integer donacijaId;
    private Integer proizvodId;

}

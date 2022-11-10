package com.example.bldonate.models.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class RezervacijaStavkaRequest {

    @NotNull
    private BigDecimal kolicina;
    @NotNull
    private Integer rezervacijaId;
    @NotNull
    private Integer donacijaStavkaId;

}

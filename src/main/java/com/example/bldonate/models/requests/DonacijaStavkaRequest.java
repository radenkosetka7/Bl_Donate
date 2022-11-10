package com.example.bldonate.models.requests;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class DonacijaStavkaRequest {

    @NotNull
    private BigDecimal kolicina;
    @NotNull
    private Integer donacijaId;
    @NotNull
    private Integer proizvodId;

}

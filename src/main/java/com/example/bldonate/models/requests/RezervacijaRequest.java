package com.example.bldonate.models.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
public class RezervacijaRequest {

    @NotNull
    private Date datumRezervacije;
    private Boolean arhivirana;
    @NotNull
    private Integer korisnikId;
}

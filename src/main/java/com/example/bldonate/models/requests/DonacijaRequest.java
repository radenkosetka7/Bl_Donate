package com.example.bldonate.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Data
public class DonacijaRequest {

    @NotNull
    private Date terminPreuzimanja;
    @NotBlank
    private String adresa;
    @NotBlank
    private String brojTelefona;
    private String napomena;
    @NotNull
    private Integer donatorName;
    private Boolean prevoz;
    private Boolean arhivirana;
    @NotNull
    private Date datumDoniranja;

}

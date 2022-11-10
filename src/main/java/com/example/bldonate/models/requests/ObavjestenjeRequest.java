package com.example.bldonate.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ObavjestenjeRequest {

    @NotBlank
    private String sadrzaj;
    private Boolean procitano;
    @NotNull
    private Integer korisnik;
}

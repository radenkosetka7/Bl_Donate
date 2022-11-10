package com.example.bldonate.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ProizvodRequest {

    @NotBlank
    private String naziv;
    @NotNull
    private Date rokUpotrebe;
    @NotNull
    private Integer kategorija;
    @NotNull
    private Integer jedinica;
}

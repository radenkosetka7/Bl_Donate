package com.example.bldonate.models.requests;

import lombok.Data;

import java.util.Date;

@Data
public class ProizvodRequest {

    private String naziv;
    private Date rokUpotrebe;
    private Integer kategorija;
    private Integer jedinica;
}

package com.example.bldonate.models.dto;


import lombok.Data;

import java.util.Date;

@Data
public class Proizvod {

    private Integer id;
    private String naziv;
    private Date rokUpotrebe;
    private String jedinica;
    private String kategorija;

}

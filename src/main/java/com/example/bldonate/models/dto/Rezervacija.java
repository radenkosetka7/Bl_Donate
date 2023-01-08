package com.example.bldonate.models.dto;


import lombok.Data;


import java.util.*;

@Data
public class Rezervacija {


    private Integer id;
    private Date datumRezervacije;
    private Boolean arhivirana;
    private String korisnik;
    private List<RezervacijaStavka> rezervacijaStavke;

}

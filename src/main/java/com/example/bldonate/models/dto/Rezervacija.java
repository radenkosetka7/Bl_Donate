package com.example.bldonate.models.dto;


import lombok.Data;


import java.util.*;

@Data
public class Rezervacija {


    private Integer id;
    private String korisnikName;
    private Date datumRezervacije;
    private Boolean arhivirana;
    private List<RezervacijaStavka> stavke;

}

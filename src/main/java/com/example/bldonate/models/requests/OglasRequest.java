package com.example.bldonate.models.requests;


import lombok.Data;

import java.util.Date;

@Data
public class OglasRequest {

    private Date datumObjave;
    private String sadrzaj;
    private Integer korisnikId;
    private Boolean prevoz;
    private Boolean namirnice;
}

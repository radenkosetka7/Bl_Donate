package com.example.bldonate.models.requests;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class OglasRequest {

    @NotNull
    private Date datumObjave;
    @NotBlank
    private String sadrzaj;
    @NotNull
    private Integer korisnikId;
    private Boolean prevoz;
    private Boolean namirnice;
}

package com.example.bldonate.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class KategorijaProizvodaRequest {

    @NotBlank
    private String nazivKategorije;
}

package com.example.bldonate.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class JedinicaMjereRequest {

    @NotBlank
    private String tip;
    @NotBlank
    private String skracenica;

}

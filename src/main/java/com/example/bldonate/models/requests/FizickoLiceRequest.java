package com.example.bldonate.models.requests;

import lombok.Data;

@Data
public class FizickoLiceRequest {

    private DonatorRequest donator;
    private String jmbg;
    private String prezime;
}


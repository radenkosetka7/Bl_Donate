package com.example.bldonate.models.requests;

import lombok.Data;

@Data
public class PravnoLiceRequest{
    private DonatorRequest donator;
    private String pib;
}

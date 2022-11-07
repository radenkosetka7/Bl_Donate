package com.example.bldonate.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends Korisnik {

    private String token;
}

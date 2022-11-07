package com.example.bldonate.models.requests;

import com.example.bldonate.models.enums.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class SignUpRequest {

    @NotBlank
    private String korisnickoIme;
    @NotBlank
    private String lozinka;
    @NotBlank
    private String ime;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String brojTelefona;
    private String adresa;
    private String prezime;
    private byte[] logo;
    private String jmbg;
    private String pib;
    @NotBlank
    private Role role;
}

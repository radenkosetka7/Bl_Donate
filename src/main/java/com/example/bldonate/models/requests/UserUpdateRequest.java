package com.example.bldonate.models.requests;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;

@Data
public class UserUpdateRequest {

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
}

package com.example.bldonate.models.requests;

import javax.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class ChangePasswordRequest {

    @NotBlank
    private String lozinka;
    @NotBlank
    private String trenutnaLozinka;
}

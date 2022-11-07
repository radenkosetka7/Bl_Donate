package com.example.bldonate.models.requests;


import com.example.bldonate.models.enums.Role;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeRoleRequest {

    @NotNull
    private Role role;
}



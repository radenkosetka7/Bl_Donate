package com.example.bldonate.models.requests;

import com.example.bldonate.models.enums.UserStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangeStatusRequest {

    @NotNull
    private UserStatus status;
}

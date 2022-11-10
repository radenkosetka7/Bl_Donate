package com.example.bldonate.models.requests;

import com.example.bldonate.models.enums.UserStatus;
import javax.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ChangeStatusRequest {

    @NotNull
    private UserStatus status;
}

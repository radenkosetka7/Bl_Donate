package com.example.bldonate.services;

import com.example.bldonate.models.dto.LoginResponse;
import com.example.bldonate.models.requests.LoginRequest;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}

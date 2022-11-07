package com.example.bldonate.services.impl;

import com.example.bldonate.exceptions.UnauthorizedException;
import com.example.bldonate.models.dto.JwtUser;
import com.example.bldonate.models.dto.LoginResponse;
import com.example.bldonate.models.requests.LoginRequest;
import com.example.bldonate.services.AuthService;
import com.example.bldonate.services.KorisnikService;
import com.example.bldonate.util.LoggingUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final KorisnikService userService;
    @Value("${authorization.token.expiration-time}")
    private String tokenExpirationTime;
    @Value("${authorization.token.secret}")
    private String tokenSecret;

    public AuthImpl(AuthenticationManager authenticationManager, KorisnikService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        LoginResponse response = null;
        try {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(), request.getPassword()
                            )
                    );
            JwtUser user = (JwtUser) authenticate.getPrincipal();
            response = userService.findById(user.getId(), LoginResponse.class);
            response.setToken(generateJwt(user));
        } catch (Exception ex) {
            LoggingUtil.logException(ex, getClass());
            throw new UnauthorizedException();
        }
        return response;
    }

    private String generateJwt(JwtUser user) {
        return Jwts.builder()
                .setId(user.getId().toString())
                .setSubject(user.getUsername())
                .claim("role", user.getRole().name())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationTime)))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }
}

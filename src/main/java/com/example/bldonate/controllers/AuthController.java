package com.example.bldonate.controllers;

import com.example.bldonate.models.dto.JwtUser;
import com.example.bldonate.models.dto.LoginResponse;
import com.example.bldonate.models.entities.KorisnikEntity;
import com.example.bldonate.models.requests.LoginRequest;
import com.example.bldonate.models.requests.SignUpRequest;
import com.example.bldonate.services.AuthService;
import com.example.bldonate.services.EmailService;
import com.example.bldonate.services.KorisnikService;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
public class AuthController {

    private final AuthService service;
    private final KorisnikService korisnikService;
    private final EmailService emailService;

    public AuthController(AuthService service, KorisnikService korisnikService, EmailService emailService) {
        this.service = service;
        this.korisnikService = korisnikService;
        this.emailService = emailService;
    }

    @PostMapping("login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        return service.login(request);
    }

    @GetMapping("state")
    public LoginResponse state(Authentication auth) {
        JwtUser jwtUser = (JwtUser) auth.getPrincipal();
        return korisnikService.findById(jwtUser.getId(), LoginResponse.class);
    }

    @PostMapping("sign-up")
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        korisnikService.signUp(request);
    }



    @GetMapping("reset_password")
    public String showResetPasswordForm(@Param(value = "token") String token, Model model)
    {
        KorisnikEntity customer = korisnikService.findByResetToken(token);
        model.addAttribute("token", token);

        if (customer == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        }

        return "reset_password_form";
    }

    @PostMapping("reset_password")
    public String processResetPassword(HttpServletRequest request, Model model)
    {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        KorisnikEntity customer = korisnikService.findByResetToken(token);
        model.addAttribute("title", "Reset your password");

        if (customer == null) {
            model.addAttribute("message", "Invalid Token");
            return "message";
        } else {
            korisnikService.updatePassword(customer, password);

            model.addAttribute("message", "You have successfully changed your password.");
        }

        return "message";
    }

}

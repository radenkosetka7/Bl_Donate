package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "administrator")
public class AdministratorEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "korisnicko_ime", nullable = false, length = 50)
    private String korisnickoIme;
    @Basic
    @Column(name = "lozinka", nullable = false, length = -1)
    private String lozinka;
    @Basic
    @Column(name = "ime", nullable = false, length = 50)
    private String ime;
    @Basic
    @Column(name = "prezime", nullable = false, length = 50)
    private String prezime;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "broj_telefona", nullable = false, length = 11)
    private String brojTelefona;

}

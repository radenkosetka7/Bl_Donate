package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "donator")
public class DonatorEntity {
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
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "broj_telefona", nullable = false, length = 11)
    private String brojTelefona;
    @Basic
    @Column(name = "adresa", nullable = false, length = 120)
    private String adresa;
    @Basic
    @Column(name = "ime", nullable = false, length = 50)
    private String ime;
    @Basic
    @Column(name = "odoboren", nullable = false)
    private Boolean odoboren;
    @Basic
    @Column(name = "logo", nullable = true)
    private byte[] logo;
    @OneToOne(mappedBy = "donator")
    @PrimaryKeyJoinColumn
    private FizickoLiceEntity fizickoLice;
    @OneToMany(mappedBy = "donator")
    private List<ObavjestenjeEntity> obavjestenjes;
    @OneToOne(mappedBy = "donator")
    @PrimaryKeyJoinColumn
    private PravnoLiceEntity pravnoLice;
    @OneToMany(mappedBy = "donator")
    private List<DonacijaEntity> donacije;

}

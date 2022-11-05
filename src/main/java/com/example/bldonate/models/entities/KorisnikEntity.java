package com.example.bldonate.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "korisnik")
public class KorisnikEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "naziv", nullable = false, length = 50)
    private String naziv;
    @Basic
    @Column(name = "adresa", nullable = false, length = 50)
    private String adresa;
    @Basic
    @Column(name = "email", nullable = false, length = 50)
    private String email;
    @Basic
    @Column(name = "broj_telefona", nullable = false, length = 50)
    private String brojTelefona;
    @Basic
    @Column(name = "odoboren", nullable = false)
    private Boolean odoboren;
    @Basic
    @Column(name = "korisnicko_ime", nullable = false, length = 50)
    private String korisnickoIme;
    @Basic
    @Column(name = "lozinka", nullable = false, length = 11)
    private String lozinka;
    @OneToMany(mappedBy = "korisnik")
    private List<ObavjestenjeEntity> obavjestenjes;
    @OneToMany(mappedBy = "korisnik")
    private List<OglasEntity> oglasis;
    @OneToMany(mappedBy = "korisnik")
    private List<RezervacijaEntity> rezervacije;

}

package com.example.bldonate.models.entities;

import com.example.bldonate.models.enums.Role;
import com.example.bldonate.models.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "korisnik")
public class KorisnikEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "korisnicko_ime", nullable = false, length = 45)
    private String korisnickoIme;
    @Basic
    @Column(name = "lozinka", nullable = false, length = -1)
    private String lozinka;
    @Basic
    @Column(name = "prezime", nullable = true, length = 45)
    private String prezime;
    @Basic
    @Column(name = "email", nullable = false, length = 45)
    private String email;
    @Basic
    @Column(name = "broj_telefona", nullable = false, length = 11)
    private String brojTelefona;
    @Basic
    @Column(name = "adresa", nullable = true, length = 45)
    private String adresa;
    @Basic
    @Column(name = "ime", nullable = false, length = 45)
    private String ime;
    @Basic
    @Column(name = "logo", nullable = true)
    private byte[] logo;
    @Basic
    @Column(name = "jmbg", nullable = true, length = 13)
    private String jmbg;
    @Basic
    @Column(name = "pib", nullable = true, length = 13)
    private String pib;
    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rola", nullable = false)
    private Role rola;
    @Basic
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status", nullable = false)
    private Status status;
    @OneToMany(mappedBy = "korisnik")
    private List<RezervacijaEntity> rezervacije;
    @OneToMany(mappedBy = "korisnik")
    private List<OglasEntity> oglasi;
    @OneToMany(mappedBy = "korisnik")
    private List<DonacijaEntity> donacije;
    @OneToMany(mappedBy = "korisnik")
    private List<ObavjestenjeEntity> obavjestenja;

    public enum Status {
        REQUESTED, ACTIVE,BLOCKED
    }

}

package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "donacija")
public class DonacijaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "termin_preuzimanja", nullable = false)
    private Date terminPreuzimanja;
    @Basic
    @Column(name = "adresa", nullable = false, length = 50)
    private String adresa;
    @Basic
    @Column(name = "broj_telefona", nullable = false, length = 11)
    private String brojTelefona;
    @Basic
    @Column(name = "napomena", nullable = true, length = -1)
    private String napomena;
    @Basic
    @Column(name = "prevoz", nullable = false)
    private Boolean prevoz;
    @Basic
    @Column(name = "arhivirana", nullable = false)
    private Boolean arhivirana;
    @Basic
    @Column(name = "datum_doniranja", nullable =false)
    private Date datumDoniranja;
    @ManyToOne
    @JoinColumn(name = "donator_id", referencedColumnName = "id", nullable = false)
    private DonatorEntity donator;
    @OneToMany(mappedBy = "donacija")
    private List<DonacijaStavkaEntity> donacijaStavke;


}

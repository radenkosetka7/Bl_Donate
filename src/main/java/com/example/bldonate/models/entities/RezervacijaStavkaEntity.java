package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "rezervacija_stavka")
public class RezervacijaStavkaEntity {
    @Basic
    @Column(name = "kolicina", nullable = false, precision = 2)
    private BigDecimal kolicina;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "rezervacija_id", referencedColumnName = "id", nullable = false)
    private RezervacijaEntity rezervacija;
    @ManyToOne
    @JoinColumn(name = "donacija_stavka_id", referencedColumnName = "id", nullable = false)
    private DonacijaStavkaEntity donacijaStavka;

}

package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "donacija_stavka")
public class DonacijaStavkaEntity {
    @Basic
    @Column(name = "kolicina", nullable = false, precision = 2)
    private BigDecimal kolicina;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @OneToMany(mappedBy = "donacijaStavka")
    private List<RezervacijaStavkaEntity> rezervacijaStavkas;
    @ManyToOne
    @JoinColumn(name = "donacija_id",nullable = false,referencedColumnName = "id")
    private DonacijaEntity donacija;
    @OneToOne()
    @JoinColumn(name = "proizvod_id",nullable = false,referencedColumnName = "id")
    private ProizvodEntity proizvod;

}

package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "rezervacija")
public class RezervacijaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "datum_rezervacije", nullable = true)
    private Date datumRezervacije;
    @Basic
    @Column(name = "arhivirana", nullable = true)
    private Boolean arhivirana;
    @ManyToOne
    @JoinColumn(name = "korisnik_id", referencedColumnName = "id", nullable = false)
    private KorisnikEntity korisnik;
    @OneToMany(mappedBy = "rezervacija")
    private List<RezervacijaStavkaEntity> rezervacijaStavke;

}

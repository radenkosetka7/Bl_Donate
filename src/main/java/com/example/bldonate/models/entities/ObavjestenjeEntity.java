package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "obavjestenje")
public class ObavjestenjeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "sadrzaj", nullable = true, length = -1)
    private String sadrzaj;
    @Basic
    @Column(name = "procitano", nullable = true)
    private Boolean procitano;

    @ManyToOne
    @JoinColumn(name = "korisnik_id", referencedColumnName = "id")
    private KorisnikEntity korisnik;

}

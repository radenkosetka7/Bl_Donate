package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "oglas")
public class OglasEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "datum_objave", nullable = false)
    private Date datumObjave;
    @Basic
    @Column(name = "sadrzaj", nullable = false, length = -1)
    private String sadrzaj;
    @Basic
    @Column(name = "prevoz", nullable = false)
    private Boolean prevoz;
    @Basic
    @Column(name = "namirnice", nullable = false)
    private Boolean namirnice;
    @ManyToOne
    @JoinColumn(name = "korisnik_id", referencedColumnName = "id", nullable = false)
    private KorisnikEntity korisnik;

}

package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@Entity
@Table(name = "proizvod")
public class ProizvodEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "naziv", nullable = false, length = 50)
    private String naziv;
    @Basic
    @Column(name = "rok_upotrebe", nullable = false)
    private Date rokUpotrebe;
    @ManyToOne
    @JoinColumn(name = "kategorija_proizvoda_id", referencedColumnName = "id", nullable = false)
    private KategorijaProizvodaEntity kategorijaProizvoda;
    @ManyToOne
    @JoinColumn(name = "jedinica_mjere_id", referencedColumnName = "id", nullable = false)
    private JedinicaMjereEntity jedinicaMjere;
    @OneToOne(mappedBy = "proizvod")
    private DonacijaStavkaEntity donacijaStavka;

}

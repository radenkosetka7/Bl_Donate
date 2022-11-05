package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "kategorija_proizvoda")
public class KategorijaProizvodaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "naziv_kategorije", nullable = false, length = 50)
    private String nazivKategorije;
    @OneToMany(mappedBy = "kategorijaProizvoda")
    private List<ProizvodEntity> proizvods;

}

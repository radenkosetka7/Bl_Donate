package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "jedinica_mjere")
public class JedinicaMjereEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic
    @Column(name = "tip", nullable = false, length = 10)
    private String tip;
    @Basic
    @Column(name = "skracenica", nullable = false, length = 10)
    private String skracenica;
    @OneToMany(mappedBy = "jedinicaMjere")
    private List<ProizvodEntity> proizvods;

}

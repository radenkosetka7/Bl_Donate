package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "pravno_lice")
public class PravnoLiceEntity {
    @Basic
    @Column(name = "pib", nullable = false, length = 13)
    private String pib;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "donator_id", nullable = false)
    private Integer id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "donator_id", referencedColumnName = "id", nullable = false)
    private DonatorEntity donator;

}

package com.example.bldonate.models.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@Table(name = "fizicko_lice")
public class FizickoLiceEntity {
    @Basic
    @Column(name = "jmbg", nullable = false, length = 13)
    private String jmbg;
    @Basic
    @Column(name = "prezime", nullable = false, length = 50)
    private String prezime;
    @Id
    @Column(name="donator_id",nullable = false)
    private Integer id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "donator_id", referencedColumnName = "id", nullable = false)
    private DonatorEntity donator;

}

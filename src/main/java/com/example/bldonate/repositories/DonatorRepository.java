package com.example.bldonate.repositories;

import com.example.bldonate.models.entities.DonatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonatorRepository extends JpaRepository<DonatorEntity,Integer> {
}

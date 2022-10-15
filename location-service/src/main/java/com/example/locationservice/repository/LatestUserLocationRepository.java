package com.example.locationservice.repository;

import com.example.locationservice.model.LatestUserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LatestUserLocationRepository extends JpaRepository<LatestUserLocation, UUID>
{
}

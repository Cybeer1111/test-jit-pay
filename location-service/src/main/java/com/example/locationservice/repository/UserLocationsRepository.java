package com.example.locationservice.repository;

import com.example.locationservice.model.UserLocations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface UserLocationsRepository extends JpaRepository<UserLocations, UUID>
{
    Optional<UserLocations> findByIdAndByCreatedOnBetween(UUID userId,
                                                          Instant startDateTime,
                                                          Instant endDateTime);
}

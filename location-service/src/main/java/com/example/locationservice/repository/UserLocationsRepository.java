package com.example.locationservice.repository;

import com.example.locationservice.model.UserLocations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UserLocationsRepository extends JpaRepository<UserLocations, UUID>
{
    Optional<UserLocations> findByUserIdAndCreatedOnBetween(UUID userId,
                                                            LocalDateTime startDateTime,
                                                            LocalDateTime endDateTime);
}

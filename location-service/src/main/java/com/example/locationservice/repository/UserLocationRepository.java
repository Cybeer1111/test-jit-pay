package com.example.locationservice.repository;

import com.example.locationservice.model.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface UserLocationRepository extends JpaRepository<UserLocation, UUID>
{
    Collection<UserLocation> findByUserIdAndCreatedOnBetween(UUID userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}

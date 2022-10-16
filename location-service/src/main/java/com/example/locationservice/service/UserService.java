package com.example.locationservice.service;

import com.example.locationservice.dto.LatestUserLocation;
import com.example.locationservice.dto.User;

import java.util.Optional;
import java.util.UUID;

/**
 * User information service interface.
 */
public interface UserService
{
    /**
     * Save or update user information.
     * @param user User information.
     */
    void saveOrUpdate(User user);

    /**
     * Get information about user.
     * @param userId User identifier.
     * @return User information.
     */
    Optional<LatestUserLocation> getUserWithLatestLocation(UUID userId);
}

package com.example.locationservice.service;

import com.example.locationservice.dto.UserLocation;
import com.example.locationservice.dto.UserLocations;
import com.example.locationservice.exception.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * User location service interface.
 */
public interface LocationService
{
    /**
     * Get user locations history by date time range.
     *
     * @param userId        User identifier.
     * @param startDateTime Start date time of date time range.
     * @param endDateTime   End date time of date time range.
     * @return User locations history.
     */
    UserLocations getUserLocationHistory(UUID userId,
                                         LocalDateTime startDateTime,
                                         LocalDateTime endDateTime);

    /**
     * Save or update user location.
     *
     * @param location user location.
     */
    void saveOrUpdate(UserLocation location) throws UserNotFoundException;
}

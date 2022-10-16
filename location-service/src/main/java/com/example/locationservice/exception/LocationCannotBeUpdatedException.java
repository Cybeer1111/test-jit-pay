package com.example.locationservice.exception;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * User not found exception.
 */
public class LocationCannotBeUpdatedException extends RuntimeException
{
    private final UUID userId;
    private final LocalDateTime createdOn;

    public LocationCannotBeUpdatedException(UUID userId, LocalDateTime createdOn)
    {
        this.userId = userId;
        this.createdOn = createdOn;
    }

    /**
     * Get user id.
     * @return user id.
     */
    public UUID getUserId()
    {
        return userId;
    }

    /**
     * Get date of user location.
     * @return Date of user location.
     */
    public LocalDateTime getCreatedOn()
    {
        return createdOn;
    }
}

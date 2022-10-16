package com.example.locationservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents user location.
 */
public class UserLocation
{
    private UUID userId;
    private LocalDateTime createdOn;
    private Location location;

    /**
     * Get user identifier.
     * @return User identifier.
     */
    public UUID getUserId()
    {
        return userId;
    }

    /**
     * Set user identifier.
     * @param userId User identifier.
     */
    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    /**
     * Get date when user was in this location.
     * @return Date when user was in this location.
     */
    public LocalDateTime getCreatedOn()
    {
        return createdOn;
    }

    /**
     * Set date when user was in this location.
     * @param createdOn Date when user was in this location.
     */
    public void setCreatedOn(LocalDateTime createdOn)
    {
        this.createdOn = createdOn;
    }

    /**
     * Get user location.
     * @return  User location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set user location.
     * @param location User location.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        return "UserLocation{" +
                "userId=" + userId +
                ", createdOn=" + createdOn +
                ", location=" + location +
                '}';
    }
}

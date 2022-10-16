package com.example.locationservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents user location.
 */
public class StampedLocation
{
    private LocalDateTime createdOn;
    private Location location = new Location();

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
     * Get location.
     * @return location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set location.
     * @param location location.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        return "StampedLocation{" +
                "createdOn=" + createdOn +
                ", location=" + location +
                '}';
    }
}

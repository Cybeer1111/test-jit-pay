package com.example.locationservice.dto;

import java.util.Collection;
import java.util.UUID;

/**
 * Represents user locations history.
 */
public class UserLocations
{
    private UUID userId;
    private Collection<StampedLocation> locations;

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
     * Get user locations collection.
     * @return User locations collection.
     */
    public Collection<StampedLocation> getLocations()
    {
        return locations;
    }

    /**
     * Set user locations collection.
     * @param locations User locations collection.
     */
    public void setLocations(Collection<StampedLocation> locations)
    {
        this.locations = locations;
    }

    @Override
    public String toString()
    {
        return "UserLocations{" +
                "userId=" + userId +
                ", locations=" + locations +
                '}';
    }
}

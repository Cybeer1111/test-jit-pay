package com.example.locationservice.model;

import java.util.Collection;
import java.util.UUID;

public class UserLocations
{
    private UUID userId;
    private Collection<UserLocation> locations;

    public UserLocations()
    {
    }

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    public Collection<UserLocation> getLocations()
    {
        return locations;
    }

    public void setLocations(Collection<UserLocation> locations)
    {
        this.locations = locations;
    }
}

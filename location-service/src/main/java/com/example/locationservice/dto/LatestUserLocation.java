package com.example.locationservice.dto;

/**
 * User info with the latest user location.
 */
public class LatestUserLocation extends User
{
    private Location location;

    /**
     * Get latest user location.
     * @return Latest user location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set latest user location.
     * @param location latest user location.
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    @Override
    public String toString()
    {
        return "LatestUserLocation{" +
                "location=" + location +
                '}';
    }
}

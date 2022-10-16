package com.example.locationservice.dto;

/**
 * Geographic location.
 */
public class Location
{
    private double latitude;
    private double longitude;

    /**
     * Get latitude of user location.
     *
     * @return Latitude of user location.
     */
    public double getLatitude()
    {
        return latitude;
    }

    /**
     * Set latitude of user location.
     *
     * @param latitude Latitude of user location.
     */
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    /**
     * Get longitude of user location.
     *
     * @return longitude of user location.
     */
    public double getLongitude()
    {
        return longitude;
    }

    /**
     * Set longitude of user location.
     *
     * @param longitude longitude of user location.
     */
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

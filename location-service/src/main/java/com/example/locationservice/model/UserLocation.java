package com.example.locationservice.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User location entity. Represents database structure.
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "createdOn" }) })
public class UserLocation
{
    @Id
    @GeneratedValue
    private UUID id;
    private UUID userId;
    private LocalDateTime createdOn;
    private double latitude;
    private double longitude;

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    /**
     * Constructor. Created for JPA purposes.
     */
    public UserLocation()
    {
    }

    /**
     * Get user identifier.
     *
     * @return User identifier.
     */
    public UUID getUserId()
    {
        return userId;
    }

    /**
     * Set user identifier.
     *
     * @param userId User identifier.
     */
    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    /**
     * Get date when user was in this location.
     *
     * @return Date when user was in this location.
     */
    public LocalDateTime getCreatedOn()
    {
        return createdOn;
    }

    /**
     * Set date when user was in this location.
     *
     * @param createdOn Date when user was in this location.
     */
    public void setCreatedOn(LocalDateTime createdOn)
    {
        this.createdOn = createdOn;
    }

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
        return "UserLocation{" +
                "id=" + id +
                ", userId=" + userId +
                ", createdOn=" + createdOn +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}

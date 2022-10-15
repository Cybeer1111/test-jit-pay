package com.example.locationservice.model;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserLocation
{
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "userId")
    private User user;
    private Instant createdOn;
    private double latitude;
    private double longitude;

    public UserLocation()
    {
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Instant getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn)
    {
        this.createdOn = createdOn;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }
}
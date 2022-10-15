package com.example.locationservice.model;

import java.util.Collection;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class UserLocations
{
    @Column(name = "id")
    private UUID userId;
    @OneToMany
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

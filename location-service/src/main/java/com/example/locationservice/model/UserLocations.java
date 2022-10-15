package com.example.locationservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "UserTable")
public class UserLocations
{
    @Id
    @Column(name = "id")
    private UUID userId;
    @JsonIgnore
    private LocalDateTime createdOn;
    @OneToMany
    private Collection<UserLocation> locations;

    public UserLocations()
    {
    }

    public LocalDateTime getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn)
    {
        this.createdOn = createdOn;
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

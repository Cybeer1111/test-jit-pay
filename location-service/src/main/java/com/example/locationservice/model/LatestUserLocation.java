package com.example.locationservice.model;

import org.hibernate.annotations.JoinFormula;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "User")
public class LatestUserLocation extends User
{
    @ManyToOne
    @JoinFormula("(SELECT ul.id FROM UserLocation ul WHERE ul.userId = id ORDER BY ul.createdOn DESC LIMIT 1)")
    private UserLocation location;

    public LatestUserLocation()
    {
    }

    public UserLocation getLocation()
    {
        return location;
    }

    public void setLocation(UserLocation location)
    {
        this.location = location;
    }
}

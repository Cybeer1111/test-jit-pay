package com.example.locationservice.model;

import org.hibernate.annotations.JoinFormula;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Special entity to get user with the latest location.
 */
@Entity
@Table(name = "UserTable")
public class LatestUserLocation extends UserSuperclass
{
    @ManyToOne
    @JoinFormula("(SELECT ul.id FROM user_location ul WHERE ul.user_id = id ORDER BY ul.created_on DESC LIMIT 1)")
    private UserLocation location;

    /**
     * Constructor. Created for JPA purposes.
     */
    public LatestUserLocation()
    {
    }

    /**
     * Get latest user location.
     * @return Latest user location.
     */
    public UserLocation getLocation()
    {
        return location;
    }

    /**
     * Set latest user location.
     * @param location latest user location.
     */
    public void setLocation(UserLocation location)
    {
        this.location = location;
    }
}

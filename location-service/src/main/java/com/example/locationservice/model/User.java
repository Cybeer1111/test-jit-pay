package com.example.locationservice.model;

import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User
{
    @Id
    @Column(name = "id")
    private UUID userId;
    private String firstName;
    private String secondName;
    private Instant createdOn;
    private String email;

    public User()
    {
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public UUID getUserId()
    {
        return userId;
    }

    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getSecondName()
    {
        return secondName;
    }

    public void setSecondName(String secondName)
    {
        this.secondName = secondName;
    }

    public Instant getCreatedOn()
    {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn)
    {
        this.createdOn = createdOn;
    }
}

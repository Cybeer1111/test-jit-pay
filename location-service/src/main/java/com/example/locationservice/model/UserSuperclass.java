package com.example.locationservice.model;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * User entity. Represents database structure.
 */
@MappedSuperclass
public class UserSuperclass
{
    @Id
    @Column(name = "id")
    private UUID userId;
    private String firstName;
    private String secondName;
    private LocalDateTime createdOn;
    private String email;

    /**
     * Constructor. Created for JPA purposes.
     */
    public UserSuperclass()
    {
    }

    /**
     * Get user first name.
     * @return User first name.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Set user first name.
     * @param firstName User first name.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Get user second name.
     * @return User second name.
     */
    public String getSecondName()
    {
        return secondName;
    }

    /**
     * Set user second name.
     * @param secondName User second name.
     */
    public void setSecondName(String secondName)
    {
        this.secondName = secondName;
    }

    /**
     * Get date when user information was updated.
     * @return Date when user information was updated.
     */
    public LocalDateTime getCreatedOn()
    {
        return createdOn;
    }

    /**
     * Set date when user information was updated.
     * @param createdOn Date when user information was updated.
     */
    public void setCreatedOn(LocalDateTime createdOn)
    {
        this.createdOn = createdOn;
    }

    /**
     * Get user email.
     * @return User email.
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Set user email.
     * @param email user email.
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Get user identifier.
     * @return User identifier.
     */
    public UUID getUserId()
    {
        return userId;
    }

    /**
     * Set user identifier.
     * @param userId User identifier.
     */
    public void setUserId(UUID userId)
    {
        this.userId = userId;
    }
}

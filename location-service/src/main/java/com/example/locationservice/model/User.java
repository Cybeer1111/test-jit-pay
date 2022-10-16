package com.example.locationservice.model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User entity.
 */
@Entity
@Table(name = "UserTable")
public class User extends UserSuperclass
{
    /**
     * Constructor. Created for JPA purposes.
     */
    public User()
    {
    }
}

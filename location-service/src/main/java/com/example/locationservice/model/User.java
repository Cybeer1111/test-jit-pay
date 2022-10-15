package com.example.locationservice.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "UserTable")
public class User extends UserSuperclass
{
    public User()
    {
    }
}

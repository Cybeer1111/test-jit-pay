package com.example.locationservice.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException
{
    private final UUID userId;

    public UserNotFoundException(UUID userId)
    {
        super("Could not find user " + userId);
        this.userId = userId;
    }

    public UUID getUserId()
    {
        return userId;
    }
}

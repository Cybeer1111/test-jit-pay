package com.example.locationservice.exception;

import java.util.UUID;

/**
 * User not found exception.
 */
public class UserNotFoundException extends RuntimeException
{
    private final UUID userId;

    public UserNotFoundException(UUID userId)
    {
        this.userId = userId;
    }

    /**
     * Get user id.
     * @return user id.
     */
    public UUID getUserId()
    {
        return userId;
    }
}

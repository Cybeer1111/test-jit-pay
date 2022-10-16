package com.example.locationservice.exception.error;

import org.springframework.http.HttpStatus;

import java.util.UUID;

/**
 * User not found error. Must be used when user is not found in database.
 */
public class UserNotFoundError implements ApplicationResponseError
{
    private final UUID userId;

    /**
     * Constructor
     * @param userId User identifier that wasn't found.
     */
    public UserNotFoundError(UUID userId)
    {
        this.userId = userId;
    }

    /**
     * Get user identifier.
     * @return User identifier.
     */
    public UUID getUserId()
    {
        return userId;
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public String getMessage()
    {
        return "User not found";
    }
}

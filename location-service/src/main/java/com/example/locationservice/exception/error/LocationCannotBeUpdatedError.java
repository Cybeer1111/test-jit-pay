package com.example.locationservice.exception.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * User not found error. Must be used when user is not found in database.
 */
public class LocationCannotBeUpdatedError implements ApplicationResponseError
{
    private final UUID userId;
    private final LocalDateTime createdOn;

    /**
     * Constructor
     *
     * @param userId    User identifier that wasn't found.
     * @param createdOn
     */
    public LocationCannotBeUpdatedError(UUID userId, LocalDateTime createdOn)
    {
        this.userId = userId;
        this.createdOn = createdOn;
    }

    /**
     * Get user identifier.
     *
     * @return User identifier.
     */
    public UUID getUserId()
    {
        return userId;
    }

    /**
     * Get date of user location.
     *
     * @return Date of user location.
     */
    public LocalDateTime getCreatedOn()
    {
        return createdOn;
    }

    @Override
    public HttpStatus getHttpStatus()
    {
        return HttpStatus.PRECONDITION_FAILED;
    }

    @Override
    public String getMessage()
    {
        return "Location cannot be updated";
    }
}

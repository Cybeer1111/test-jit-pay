package com.example.locationservice.model.key;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Composite  key for user location entity.
 */
public class UserLocationKey
{
    private UUID userId;
    private LocalDateTime createdOn;

    public UserLocationKey(UUID userId, LocalDateTime createdOn)
    {
        this.userId = userId;
        this.createdOn = createdOn;
    }
}

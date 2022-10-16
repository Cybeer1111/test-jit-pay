package com.example.locationservice.exception;

import com.example.locationservice.exception.error.ApplicationResponseError;

/**
 * Application response exception. Should be used when application wants to provide to user business error.
 */
public class ApplicationResponseException extends RuntimeException
{
    private final ApplicationResponseError error;

    public ApplicationResponseException(ApplicationResponseError error)
    {
        this.error = error;
    }

    public ApplicationResponseError getError()
    {
        return error;
    }
}

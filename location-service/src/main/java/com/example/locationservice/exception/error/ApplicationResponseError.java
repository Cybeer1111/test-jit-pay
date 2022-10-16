package com.example.locationservice.exception.error;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

/**
 * Interface for every application response error.
 */
public interface ApplicationResponseError
{
    /**
     * Get HTTP response status.
     * @return HTTP response status.
     */
    @JsonIgnore
    HttpStatus getHttpStatus();

    /**
     * Get message provided to client.
     * @return Message provided to client.
     */
    String getMessage();
}

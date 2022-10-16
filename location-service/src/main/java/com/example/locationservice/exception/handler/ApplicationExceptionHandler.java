package com.example.locationservice.exception.handler;

import com.example.locationservice.exception.ApplicationResponseException;
import com.example.locationservice.exception.error.ApplicationResponseError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Exception handler for business logic exceptions.
 */
@ControllerAdvice
public class ApplicationExceptionHandler
{
    /**
     * Exception handler for ApplicationResponseException.
     * @see ApplicationResponseException
     * @param e Application response exception.
     * @return Response entity provided to client.
     */
    @ExceptionHandler(ApplicationResponseException.class)
    public ResponseEntity<ApplicationResponseError> handleException(ApplicationResponseException e)
    {
        return new ResponseEntity<>(e.getError(), e.getError().getHttpStatus());
    }
}
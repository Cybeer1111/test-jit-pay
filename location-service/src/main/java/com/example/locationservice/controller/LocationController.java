package com.example.locationservice.controller;

import com.example.locationservice.dto.UserLocation;
import com.example.locationservice.dto.UserLocations;
import com.example.locationservice.exception.ApplicationResponseException;
import com.example.locationservice.exception.LocationCannotBeUpdatedException;
import com.example.locationservice.exception.UserNotFoundException;
import com.example.locationservice.exception.error.LocationCannotBeUpdatedError;
import com.example.locationservice.exception.error.UserNotFoundError;
import com.example.locationservice.service.LocationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Controller for operations with user location
 */
@RestController()
public class LocationController
{
    private static final Logger logger = LogManager.getLogger(LocationController.class);

    @Autowired
    private LocationService locationService;

    /**
     * Save or update user location.
     *
     * @param location user location.
     */
    @PostMapping("/location")
    void saveOrUpdate(@RequestBody UserLocation location)
    {
        try
        {
            locationService.saveOrUpdate(location);
        }
        catch (UserNotFoundException userNotFoundException)
        {
            throw new ApplicationResponseException(new UserNotFoundError(userNotFoundException.getUserId()));
        }
        catch (LocationCannotBeUpdatedException locationCannotBeUpdatedException)
        {
            throw new ApplicationResponseException(
                    new LocationCannotBeUpdatedError(locationCannotBeUpdatedException.getUserId(), locationCannotBeUpdatedException.getCreatedOn()));
        }
    }

    /**
     * Get user locations history by date time range.
     *
     * @param userId        User identifier.
     * @param startDateTime Start date time of date time range.
     * @param endDateTime   End date time of date time range.
     * @return User locations history.
     */
    @GetMapping(value = "/location/history/{userId}", params = {"startDateTime", "endDateTime"})
    UserLocations getUserLocationHistory(@PathVariable UUID userId,
                                         @RequestParam
                                         @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
                                                 LocalDateTime startDateTime,
                                         @RequestParam
                                         @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
                                                 LocalDateTime endDateTime)
    {
        try
        {
            var ret = locationService.getUserLocationHistory(userId, startDateTime, endDateTime);
            logger.debug("Returned instance: " + ret);
            return ret;
        }
        catch (UserNotFoundException userNotFoundException)
        {
            throw new ApplicationResponseException(new UserNotFoundError(userNotFoundException.getUserId()));
        }
    }
}


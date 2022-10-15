package com.example.locationservice.controller;

import com.example.locationservice.model.LatestUserLocation;
import com.example.locationservice.model.User;
import com.example.locationservice.exception.UserNotFoundException;
import com.example.locationservice.model.UserLocation;
import com.example.locationservice.model.UserLocations;
import com.example.locationservice.repository.LatestUserLocationRepository;
import com.example.locationservice.repository.UserLocationRepository;
import com.example.locationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LatestUserLocationRepository latestUserLocationRepository;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @PostMapping("/user")
    void insertOrUpdate(@RequestBody User user)
    {
        userRepository.save(user);
    }

    @PostMapping("/user/location")
    void insertOrUpdate(@RequestBody UserLocation location)
    {
        userLocationRepository.save(location);
    }

    @GetMapping("/user/location/latest/{userId}")
    LatestUserLocation one(@PathVariable UUID userId)
    {
        return latestUserLocationRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @GetMapping(value = "/user/location/history/{userId}", params = {"startDateTime", "endDateTime"})
    UserLocations one(@PathVariable UUID userId,
                      @RequestParam
                      @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
                              LocalDateTime startDateTime,
                      @RequestParam
                      @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
                              LocalDateTime endDateTime)
    {
        var locations = new UserLocations();

        locations.setUserId(userId);
        locations.setLocations(userLocationRepository.findByUserIdAndCreatedOnBetween(userId, startDateTime, endDateTime));

        if (locations.getLocations() == null || locations.getLocations().isEmpty())
        {
            ResponseEntity.noContent().build();
        }

        return locations;
    }
}

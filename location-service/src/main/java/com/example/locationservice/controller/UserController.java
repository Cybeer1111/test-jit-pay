package com.example.locationservice.controller;

import com.example.locationservice.model.LatestUserLocation;
import com.example.locationservice.model.User;
import com.example.locationservice.exception.UserNotFoundException;
import com.example.locationservice.model.UserLocations;
import com.example.locationservice.repository.LatestUserLocationRepository;
import com.example.locationservice.repository.UserLocationsRepository;
import com.example.locationservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RestController
public class UserController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserLocationsRepository userLocationsRepository;

    @Autowired
    private LatestUserLocationRepository latestUserLocationRepository;

    @PostMapping("/user")
    void insertOrUpdate(@RequestBody User user)
    {
        userRepository.save(user);
    }

    @GetMapping("/user/location/latest/{userId}")
    LatestUserLocation one(@PathVariable UUID userId)
    {
        return latestUserLocationRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @GetMapping(value = "/user/location/history/{userId}", params = {"startDateTime", "endDateTime"})
    UserLocations one(@PathVariable UUID userId, @RequestParam Instant startDateTime, @RequestParam Instant endDateTime)
    {
        return userLocationsRepository.findByIdAndByCreatedOnBetween(userId, startDateTime, endDateTime)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}

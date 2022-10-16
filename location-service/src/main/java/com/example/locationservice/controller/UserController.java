package com.example.locationservice.controller;

import com.example.locationservice.dto.LatestUserLocation;
import com.example.locationservice.dto.User;
import com.example.locationservice.exception.ApplicationResponseException;
import com.example.locationservice.exception.error.UserNotFoundError;
import com.example.locationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Controller for operations with user
 */
@RestController()
public class UserController
{
    @Autowired
    private UserService userService;

    /**
     * Save or update user information.
     *
     * @param user User information.
     */
    @PostMapping("/user")
    void saveOrUpdate(@RequestBody User user)
    {
        userService.saveOrUpdate(user);
    }

    /**
     * Get information about user.
     *
     * @param userId User identifier.
     * @return User information.
     */
    @GetMapping("/user/location/{userId}")
    LatestUserLocation getUserWithLatestLocation(@PathVariable UUID userId)
    {
        return userService.getUserWithLatestLocation(userId)
                .orElseThrow(() -> new ApplicationResponseException(new UserNotFoundError(userId)));
    }
}
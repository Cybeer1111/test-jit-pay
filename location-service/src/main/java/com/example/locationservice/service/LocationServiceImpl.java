package com.example.locationservice.service;

import com.example.locationservice.dto.StampedLocation;
import com.example.locationservice.dto.UserLocation;
import com.example.locationservice.dto.UserLocations;
import com.example.locationservice.exception.LocationCannotBeUpdatedException;
import com.example.locationservice.exception.UserNotFoundException;
import com.example.locationservice.repository.UserLocationRepository;
import com.example.locationservice.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

@Component
public class LocationServiceImpl implements LocationService
{
    private static final Logger logger = LogManager.getLogger(LocationServiceImpl.class);

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserLocations getUserLocationHistory(UUID userId, LocalDateTime startDateTime, LocalDateTime endDateTime) throws UserNotFoundException
    {
        if (!userRepository.existsById(userId))
        {
            throw new UserNotFoundException(userId);
        }
        var locations = new UserLocations();
        locations.setUserId(userId);
        locations.setLocations(userLocationRepository.findByUserIdAndCreatedOnBetween(userId, startDateTime, endDateTime)
                .parallelStream().map(e -> modelMapper.map(e, StampedLocation.class))
                .collect(Collectors.toCollection(ArrayList::new)));
        return locations;
    }

    @Override
    @Transactional
    public void saveOrUpdate(UserLocation location) throws UserNotFoundException, LocationCannotBeUpdatedException
    {
        if (!userRepository.existsById(location.getUserId()))
        {
            throw new UserNotFoundException(location.getUserId());
        }
        try
        {
            userLocationRepository.saveAndFlush(modelMapper.map(location, com.example.locationservice.model.UserLocation.class));
        }
        catch (Exception e)
        {
            throw new LocationCannotBeUpdatedException(location.getUserId(), location.getCreatedOn());
        }
    }
}

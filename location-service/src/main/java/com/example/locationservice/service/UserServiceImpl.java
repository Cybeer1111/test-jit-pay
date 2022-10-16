package com.example.locationservice.service;

import com.example.locationservice.dto.LatestUserLocation;
import com.example.locationservice.dto.Location;
import com.example.locationservice.dto.User;
import com.example.locationservice.model.UserLocation;
import com.example.locationservice.repository.LatestUserLocationRepository;
import com.example.locationservice.repository.UserRepository;
import net.bytebuddy.dynamic.DynamicType;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService
{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LatestUserLocationRepository latestUserLocationRepository;

    @Override
    public void saveOrUpdate(User user)
    {
        userRepository.save(modelMapper.map(user, com.example.locationservice.model.User.class));
    }

    @Override
    public Optional<LatestUserLocation> getUserWithLatestLocation(UUID userId)
    {
        var userLocation = latestUserLocationRepository.findById(userId);
        return userLocation.map(latestUserLocation -> modelMapper.map(latestUserLocation, LatestUserLocation.class));
    }
}

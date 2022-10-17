package com.example.locationservice.converter;

import com.example.locationservice.dto.UserLocation;
import org.modelmapper.AbstractConverter;

public class UserLocationDTOToUserLocationModelConverter extends AbstractConverter<UserLocation, com.example.locationservice.model.UserLocation>
{
    @Override
    protected com.example.locationservice.model.UserLocation convert(UserLocation userLocation)
    {
        if (userLocation == null)
        {
            return null;
        }
        var location = new com.example.locationservice.model.UserLocation();
        location.setLatitude(userLocation.getLocation().getLatitude());
        location.setLongitude(userLocation.getLocation().getLongitude());
        location.setUserId(userLocation.getUserId());
        location.setCreatedOn(userLocation.getCreatedOn());
        return location;
    }
}

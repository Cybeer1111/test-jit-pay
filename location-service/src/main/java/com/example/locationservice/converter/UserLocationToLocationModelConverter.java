package com.example.locationservice.converter;

import com.example.locationservice.dto.Location;
import com.example.locationservice.model.UserLocation;
import org.modelmapper.AbstractConverter;

public class UserLocationToLocationModelConverter extends AbstractConverter<UserLocation, Location>
{
    @Override
    protected Location convert(UserLocation userLocation)
    {
        var location = new Location();
        location.setLatitude(userLocation.getLatitude());
        location.setLongitude(userLocation.getLongitude());
        return location;
    }
}

package com.example.locationservice.converter;

import com.example.locationservice.dto.StampedLocation;
import com.example.locationservice.model.UserLocation;
import org.modelmapper.AbstractConverter;

public class UserLocationToStampedLocationModelConverter extends AbstractConverter<UserLocation, StampedLocation>
{
    @Override
    protected StampedLocation convert(UserLocation location)
    {
        if (location == null)
        {
            return null;
        }
        StampedLocation stampedLocation = new StampedLocation();
        stampedLocation.setCreatedOn(location.getCreatedOn());
        stampedLocation.getLocation().setLatitude(location.getLatitude());
        stampedLocation.getLocation().setLongitude(location.getLongitude());
        return stampedLocation;
    }
}

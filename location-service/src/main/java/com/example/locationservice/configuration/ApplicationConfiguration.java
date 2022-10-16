package com.example.locationservice.configuration;

import com.example.locationservice.converter.UserLocationDTOToUserLocationModelConverter;
import com.example.locationservice.converter.UserLocationToLocationModelConverter;
import com.example.locationservice.converter.UserLocationToStampedLocationModelConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration
 */
@Configuration
public class ApplicationConfiguration
{
    /**
     * Model to DTO and vise versa mapper.
     *
     * @return Object mapper.
     */
    @Bean
    public ModelMapper modelMapper()
    {
        var mapper = new ModelMapper();

        mapper.addConverter(new UserLocationToStampedLocationModelConverter());
        mapper.addConverter(new UserLocationToLocationModelConverter());
        mapper.addConverter(new UserLocationDTOToUserLocationModelConverter());

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return mapper;
    }
}

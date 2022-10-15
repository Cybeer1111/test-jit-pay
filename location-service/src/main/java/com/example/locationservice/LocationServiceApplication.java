package com.example.locationservice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static java.lang.Thread.setDefaultUncaughtExceptionHandler;

@SpringBootApplication
public class LocationServiceApplication
{
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args)
    {
        setDefaultUncaughtExceptionHandler((thread, e) -> log.error("Unexpected error", e));
        SpringApplication.run(LocationServiceApplication.class, args);
    }

}

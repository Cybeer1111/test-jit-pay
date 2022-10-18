package com.example.locationservice.container;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Postgres test docker container
 */
public class TestPostgresContainer extends PostgreSQLContainer<TestPostgresContainer>
{
    private static final String IMAGE_VERSION = "postgres:9.5";
    private static TestPostgresContainer container;

    private TestPostgresContainer()
    {
        super(IMAGE_VERSION);
    }

    static
    {
        getInstance().start();
    }

    public static TestPostgresContainer getInstance()
    {
        if (container == null)
        {
            container = new TestPostgresContainer();
        }
        return container;
    }

    @Override
    public void start()
    {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop()
    {
        //do nothing, JVM handles shut down
    }
}

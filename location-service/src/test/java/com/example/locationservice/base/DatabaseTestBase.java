package com.example.locationservice.base;

import com.example.locationservice.container.TestPostgresContainer;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Base class for test with real database. One DB instance for all tests.
 * We want use one DB for all tests because DB instantiation process is resource consuming.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class DatabaseTestBase
{
    /**
     * DateTime format used in messaging
     */
    protected final String COMMON_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @ClassRule
    public TestPostgresContainer postgres = TestPostgresContainer
            .getInstance();


}

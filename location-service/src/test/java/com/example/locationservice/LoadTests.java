package com.example.locationservice;

import com.example.locationservice.base.DatabaseTestBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Load tests
 * Load requirements:
 * 1. 100 request in minute for put location request.
 * 2. 10 request in minute for save or update user request.
 * 3. 10 request in minute for get user request.
 * 4. 10 request in minute for get user history request.
 * <p>
 * //TODO: Load test and application must be executed on different hosts to properly emulate load and resource consuming.
 * For load test should be user framework like Citrus.
 * Additional requirements for load testing should contains requirements like this:
 * 1. Database already had Х users.
 * 2. Database already had Y mln locations
 * Database should be loaded with data before test.
 * Also, load test should use another application properties file (another active profile and context configuration)
 */
public class LoadTests extends DatabaseTestBase
{
    private static final Logger logger = LogManager.getLogger(LoadTests.class);

    @Autowired
    private MockMvc mockMvc;

    /**
     * Load tests
     *
     * @throws Exception Test exception
     */
    @ParameterizedTest
    @CsvSource(value = {"100,10,10,10,10"}, delimiter = ',')
    public void insertUsers(int putLocationRequestsInMinute,
                            int saveUserRequestsInMinute,
                            int getUserRequestsInMinute,
                            int getUserHistoryRequestsInMinute,
                            int sendThreads) throws Exception
    {
        logger.info("Performing load test...");

        //TODO: Templates for all tests must be generated from swagger schema
        var putUserRequestTemplate = "{\n" +
                "\"userId\": \"%s\",\n" +
                "\"createdOn\": \"%s\",\n" +
                "\"email\": \"email@example.xom\",\n" +
                "\"firstName\": \"Sergei\",\n" +
                "\"secondName\": \"Gorbunov\"\n" +
                "}";

        var putLocationRequestTemplate = "{\n" +
                "\"userId\": \"%s\",\n" +
                "\"createdOn\": \"%s\",\n" +
                "\"location\": {\n" +
                "\"latitude\": %s,\n" +
                "\"longitude\": %s\n" +
                "}\n" +
                "}";

        var executor = Executors.newFixedThreadPool(sendThreads);

        var userIds = new ConcurrentHashMap<Integer, UUID>();
        var exceptions = Collections.newSetFromMap(new ConcurrentHashMap<Exception, Boolean>());
        var maxTimeForExecutionInMillis = 60000; //1 minute

        /////////////////////////////////////////////////////////////////
        // Insert users
        /////////////////////////////////////////////////////////////////

        var testStartTime = System.currentTimeMillis();
        var insertNumber = new AtomicInteger(0);
        final var countDownLatch = new CountDownLatch(saveUserRequestsInMinute);

        for (int i = 0; i < saveUserRequestsInMinute; i++)
        {
            executor.execute(() -> {
                var userId = UUID.randomUUID();
                var createdOn = LocalDateTime.now().format(DateTimeFormatter.ofPattern(COMMON_DATE_FORMAT));
                try
                {
                    mockMvc.perform(post("/user")
                                    .content(String.format(putUserRequestTemplate, userId, createdOn))
                                    .contentType(MediaType.APPLICATION_JSON))
                            .andExpect(status().isOk());
                }
                catch (Exception e)
                {
                    logger.error("Exception while inserting users", e);
                    exceptions.add(e);
                }
                finally
                {
                    userIds.put(insertNumber.getAndIncrement(), userId);
                    countDownLatch.countDown();
                }
            });
        }

        //waiting while all requests ends their execution
        if (!countDownLatch.await(maxTimeForExecutionInMillis, TimeUnit.MILLISECONDS))
        {
            throw new RuntimeException("Test failed - user inserting process took more than " + maxTimeForExecutionInMillis + "millis");
        }

        if (maxTimeForExecutionInMillis < System.currentTimeMillis() - testStartTime)
        {
            throw new RuntimeException("Test failed - user inserting process took more than " + maxTimeForExecutionInMillis + "millis");
        }

        if (!exceptions.isEmpty())
        {
            throw new RuntimeException("Exception while inserting users");
        }

        logger.info("Users inserted in " + (System.currentTimeMillis() - testStartTime) + " millis");

        /////////////////////////////////////////////////////////////////
        // Other operations
        /////////////////////////////////////////////////////////////////

        exceptions.clear();
        final var operationsCountDownLatch = new CountDownLatch(putLocationRequestsInMinute + getUserHistoryRequestsInMinute + getUserRequestsInMinute);

        /////////////////////////////////////////////////////////////////
        // Inserting locations using previous inserted user ids.
        /////////////////////////////////////////////////////////////////
        for (int i = 0; i < putLocationRequestsInMinute; i++)
        {
            executor.execute(() -> {
                var random = ThreadLocalRandom.current();
                var createdOn = LocalDateTime.of(
                        random.nextInt(1900, 3000),
                        random.nextInt(1, 12),
                        random.nextInt(1, 29),
                        random.nextInt(0, 23),
                        random.nextInt(0, 59),
                        random.nextInt(0, 59)
                ).format(DateTimeFormatter.ofPattern(COMMON_DATE_FORMAT));
                try
                {
                    mockMvc.perform(post("/location")
                            .content(String.format(putLocationRequestTemplate,
                                    userIds.get(random.nextInt(userIds.size() - 1)),
                                    createdOn,
                                    random.nextDouble(-90, 90),
                                    random.nextDouble(-180, 180)))
                            .contentType(MediaType.APPLICATION_JSON));
                }
                catch (Exception e)
                {
                    logger.error("Exception while inserting locations", e);
                    exceptions.add(e);

                }
                finally
                {
                    operationsCountDownLatch.countDown();
                }
            });
        }

        /////////////////////////////////////////////////////////////////
        // Getting users using previous inserted user ids.
        /////////////////////////////////////////////////////////////////
        for (int i = 0; i < getUserRequestsInMinute; i++)
        {
            executor.execute(() -> {
                try
                {
                    var random = ThreadLocalRandom.current();
                    mockMvc.perform(get("/user/location/" + userIds.get(random.nextInt(userIds.size() - 1)).toString()))
                            .andExpect(status().isOk());
                }
                catch (Exception e)
                {
                    logger.error("Exception while getting user", e);
                    exceptions.add(e);

                }
                finally
                {
                    operationsCountDownLatch.countDown();
                }
            });
        }

        /////////////////////////////////////////////////////////////////
        // Getting user history using previous inserted user ids.
        /////////////////////////////////////////////////////////////////
        for (int i = 0; i < getUserHistoryRequestsInMinute; i++)
        {
            executor.execute(() -> {
                try
                {
                    var random = ThreadLocalRandom.current();
                    //TODO: provide custom dates for each request
                    mockMvc.perform(get("/location/history/" + userIds.get(random.nextInt(userIds.size() - 1)).toString() +
                                    "?startDateTime=2022-02-08T11:44:04.524&endDateTime=2022-02-08T11:44:05.524"))
                            .andExpect(status().isOk());
                }
                catch (Exception e)
                {
                    logger.error("Exception while getting user location history", e);
                    exceptions.add(e);

                }
                finally
                {
                    operationsCountDownLatch.countDown();
                }
            });
        }

        // waiting while all requests ends their execution
        if (!operationsCountDownLatch.await(maxTimeForExecutionInMillis, TimeUnit.MILLISECONDS))
        {
            throw new RuntimeException("Test failed - operations requests not started in time, process took more than " + maxTimeForExecutionInMillis + " millis");
        }

        if (maxTimeForExecutionInMillis < System.currentTimeMillis() - testStartTime)
        {
            throw new RuntimeException("Test failed - process took more than " + maxTimeForExecutionInMillis + "millis");
        }

        if (!exceptions.isEmpty())
        {
            throw new RuntimeException("Exception while testing, look logs for additional details");
        }

        logger.info("Load test processed in " + (System.currentTimeMillis() - testStartTime) + " millis");
    }
}

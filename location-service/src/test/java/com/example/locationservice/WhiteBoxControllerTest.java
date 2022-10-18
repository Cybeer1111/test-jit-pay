package com.example.locationservice;

import com.example.locationservice.base.DatabaseTestBase;
import com.example.locationservice.repository.LatestUserLocationRepository;
import com.example.locationservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Some white box tests for example
 */
public class WhiteBoxControllerTest extends DatabaseTestBase
{
    private static final String commonDateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LatestUserLocationRepository latestUserLocationRepository;

    /**
     * Check that application correctly saves user data to DB
     * @param createdOn createdOn
     * @param email email
     * @param firstName email
     * @param secondName secondName
     * @throws Exception Test exception
     */
    @ParameterizedTest
    @CsvSource(value = {"2022-02-08T11:44:00.524,alex.schmid@gmail.com,Alex,Schmidt"}, delimiter = ',')
    public void putUserSuccessTest(String createdOn, String email, String firstName, String secondName) throws Exception
    {
        //Generating unique ID because DB is common for all tests.
        var userIdGuid = UUID.randomUUID();
        var userId = userIdGuid.toString();

        String putUserRequest = String.format("{\n" +
                "\"userId\": \"%s\",\n" +
                "\"createdOn\": \"%s\",\n" +
                "\"email\": \"%s\",\n" +
                "\"firstName\": \"%s\",\n" +
                "\"secondName\": \"%s\"\n" +
                "}", userId, createdOn, email, firstName, secondName);

        // Put user in database
        mockMvc.perform(post("/user")
                        .content(putUserRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Check result
        var user = userRepository.findById(userIdGuid);
        assertTrue(user.isPresent());
        assertEquals(user.get().getUserId(), userIdGuid);
        assertEquals(user.get().getCreatedOn(), LocalDateTime.parse(createdOn, DateTimeFormatter.ofPattern(commonDateFormat)));
        assertEquals(user.get().getEmail(), email);
        assertEquals(user.get().getFirstName(), firstName);
        assertEquals(user.get().getSecondName(), secondName);
    }

    /**
     * Test that user and his location correctly saved to DB
     * @param createdOn createdOn
     * @param email email
     * @param firstName firstName
     * @param secondName secondName
     * @param longitude longitude
     * @param latitude latitude
     * @param locationCreatedOn locationCreatedOn
     * @throws Exception Test exception
     */
    @ParameterizedTest
    @CsvSource(value = {"2022-02-08T11:44:00.524," +
            "alex.schmid@gmail.com,Alex,Schmidt,52.25742342295784,10.540583401747605,2022-02-08T11:45:00.524"}, delimiter = ',')
    public void putUserLocationSuccessTest(String createdOn,
                                           String email,
                                           String firstName,
                                           String secondName,
                                           String longitude,
                                           String latitude,
                                           String locationCreatedOn) throws Exception
    {
        //Generating unique ID because DB is common for all tests.
        var userIdGuid = UUID.randomUUID();
        var userId = userIdGuid.toString();

        // Put user in database
        String putUserRequest = String.format("{\n" +
                "\"userId\": \"%s\",\n" +
                "\"createdOn\": \"%s\",\n" +
                "\"email\": \"%s\",\n" +
                "\"firstName\": \"%s\",\n" +
                "\"secondName\": \"%s\"\n" +
                "}", userId, createdOn, email, firstName, secondName);

        mockMvc.perform(post("/user")
                        .content(putUserRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Put location in database
        String putLocationRequest = String.format("{\n" +
                "\"userId\": \"%s\",\n" +
                "\"createdOn\": \"%s\",\n" +
                "\"location\": {\n" +
                "\"latitude\": %s,\n" +
                "\"longitude\": %s\n" +
                "}\n" +
                "}", userId, locationCreatedOn, latitude, longitude);

        mockMvc.perform(post("/location")
                        .content(putLocationRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Check data from DB
        var user = latestUserLocationRepository.findById(userIdGuid);
        assertTrue(user.isPresent());
        assertEquals(user.get().getUserId(), userIdGuid);
        assertEquals(user.get().getCreatedOn(), LocalDateTime.parse(createdOn, DateTimeFormatter.ofPattern(commonDateFormat)));
        assertEquals(user.get().getEmail(), email);
        assertEquals(user.get().getFirstName(), firstName);
        assertEquals(user.get().getSecondName(), secondName);
        assertEquals(user.get().getLocation().getLatitude(), Double.parseDouble(latitude));
        assertEquals(user.get().getLocation().getLongitude(), Double.parseDouble(longitude));
    }
}

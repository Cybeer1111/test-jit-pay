package com.example.locationservice;

import com.example.locationservice.base.DatabaseTestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Some black box tests for example
 */
public class BlackBoxControllerTest extends DatabaseTestBase
{
    @Autowired
    private MockMvc mockMvc;

    /**
     * Check that
     * 1. user and location information a correctly saved and fetched.
     * 2. fetched last location
     *
     * @throws Exception Text exception
     */
    @Test
    public void userAndLocationSaveSuccessTest() throws Exception
    {
        String putUserRequest = "{\n" +
                "\"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b1\",\n" +
                "\"createdOn\": \"2022-02-08T11:44:00.524\",\n" +
                "\"email\": \"alex.schmid@gmail.com\",\n" +
                "\"firstName\": \"Alex\",\n" +
                "\"secondName\": \"Schmid\"\n" +
                "}";

        String putLocationRequest = "{\n" +
                "\"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b1\",\n" +
                "\"createdOn\": \"2022-02-08T11:44:03.524\",\n" +
                "\"location\": {\n" +
                "\"latitude\": 52.25742342295784,\n" +
                "\"longitude\": 10.540583401747605\n" +
                "}\n" +
                "}";

        String putLocationRequest2 = "{\n" +
                "\"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b1\",\n" +
                "\"createdOn\": \"2022-02-08T11:44:04.524\",\n" +
                "\"location\": {\n" +
                "\"latitude\": 52.25742342295782,\n" +
                "\"longitude\": 10.540583401747602\n" +
                "}\n" +
                "}";

        String getUserResponse = "{\n" +
                "    \"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b1\",\n" +
                "    \"firstName\": \"Alex\",\n" +
                "    \"secondName\": \"Schmid\",\n" +
                "    \"createdOn\": \"2022-02-08T11:44:00.524\",\n" +
                "    \"email\": \"alex.schmid@gmail.com\",\n" +
                "    \"location\": {\n" +
                "        \"latitude\": 52.25742342295782,\n" +
                "        \"longitude\": 10.540583401747602\n" +
                "    }\n" +
                "}";

        mockMvc.perform(post("/user")
                        .content(putUserRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/location")
                        .content(putLocationRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/location")
                        .content(putLocationRequest2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/location/2e3b11b0-07a4-4873-8de5-d2ae2eab25b1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getUserResponse));
    }

    /**
     * Check that location with the same userId and createdOn date can not be updated.
     *
     * @throws Exception Text exception.
     */
    @Test
    public void locationShouldNotBeUpdatedTest() throws Exception
    {
        String putUserRequest = "{\n" +
                "\"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b2\",\n" +
                "\"createdOn\": \"2022-02-08T11:44:00.524\",\n" +
                "\"email\": \"alex.schmid@gmail.com\",\n" +
                "\"firstName\": \"Alex\",\n" +
                "\"secondName\": \"Schmid\"\n" +
                "}";

        String putLocationRequest = "{\n" +
                "\"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b2\",\n" +
                "\"createdOn\": \"2022-02-08T11:44:03.524\",\n" +
                "\"location\": {\n" +
                "\"latitude\": 52.25742342295784,\n" +
                "\"longitude\": 10.540583401747605\n" +
                "}\n" +
                "}";

        mockMvc.perform(post("/user")
                        .content(putUserRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/location")
                        .content(putLocationRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // When put the same location should receive 412
        mockMvc.perform(post("/location")
                        .content(putLocationRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.PRECONDITION_FAILED.value()));
    }

    /**
     * Check that locations are correctly fetched between sent dates
     *
     * @throws Exception Text exception
     */
    @Test
    public void correctLocationHistorySelectTest() throws Exception
    {
        var putUserRequest = "{\n" +
                "\"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b3\",\n" +
                "\"createdOn\": \"2022-02-08T11:44:00.524\",\n" +
                "\"email\": \"alex.schmid@gmail.com\",\n" +
                "\"firstName\": \"Alex\",\n" +
                "\"secondName\": \"Schmid\"\n" +
                "}";

        var putLocationRequestTemplate = "{\n" +
                "\"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b3\",\n" +
                "\"createdOn\": \"%s\",\n" +
                "\"location\": {\n" +
                "\"latitude\": 52.25742342295784,\n" +
                "\"longitude\": 10.540583401747605\n" +
                "}\n" +
                "}";

        var putLocationRequest1 = String.format(putLocationRequestTemplate, "2022-02-08T11:44:03.524");
        var putLocationRequest2 = String.format(putLocationRequestTemplate, "2022-02-08T11:44:04.524");
        var putLocationRequest3 = String.format(putLocationRequestTemplate, "2022-02-08T11:44:05.524");
        var putLocationRequest4 = String.format(putLocationRequestTemplate, "2022-02-08T11:44:06.524");

        var getUserHistoryResponse = "{\n" +
                "    \"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b3\",\n" +
                "    \"locations\": [\n" +
                "        {\n" +
                "            \"createdOn\": \"2022-02-08T11:44:04.524\",\n" +
                "            \"location\": {\n" +
                "                \"latitude\": 52.25742342295784,\n" +
                "                \"longitude\": 10.540583401747605\n" +
                "            }\n" +
                "        },\n" +
                "\t\t{\n" +
                "            \"createdOn\": \"2022-02-08T11:44:05.524\",\n" +
                "            \"location\": {\n" +
                "                \"latitude\": 52.25742342295784,\n" +
                "                \"longitude\": 10.540583401747605\n" +
                "            }\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        mockMvc.perform(post("/user")
                        .content(putUserRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(post("/location")
                        .content(putLocationRequest1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/location")
                        .content(putLocationRequest2)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/location")
                        .content(putLocationRequest3)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(post("/location")
                        .content(putLocationRequest4)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/location/history/2e3b11b0-07a4-4873-8de5-d2ae2eab25b3" +
                        "?startDateTime=2022-02-08T11:44:04.524&endDateTime=2022-02-08T11:44:05.524"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getUserHistoryResponse));
    }

    /**
     * Check that location history response will return HTTP code 404 and special JSON
     *
     * @throws Exception Text exception
     */
    @Test
    public void historyUserNotFoundTest() throws Exception
    {
        var getHistoryResponse = "{\n" +
                "    \"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b4\",\n" +
                "    \"message\": \"User not found\"\n" +
                "}";

        mockMvc.perform(get("/location/history/2e3b11b0-07a4-4873-8de5-d2ae2eab25b4" +
                        "?startDateTime=2022-02-08T11:44:04.524&endDateTime=2022-02-08T11:44:05.524"))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getHistoryResponse));
    }

    /**
     * Check that post location response will return HTTP code 404 and special JSON
     *
     * @throws Exception Text exception
     */
    @Test
    public void locationUserNotFoundTest() throws Exception
    {
        var putLocationRequest = "{\n" +
                "\"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b5\",\n" +
                "\"createdOn\": \"2022-02-08T11:44:05.524\",\n" +
                "\"location\": {\n" +
                "\"latitude\": 52.25742342295784,\n" +
                "\"longitude\": 10.540583401747605\n" +
                "}\n" +
                "}";

        var putLocationResponse = "{\n" +
                "    \"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b5\",\n" +
                "    \"message\": \"User not found\"\n" +
                "}";

        mockMvc.perform(post("/location")
                        .content(putLocationRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(putLocationResponse));
    }
}

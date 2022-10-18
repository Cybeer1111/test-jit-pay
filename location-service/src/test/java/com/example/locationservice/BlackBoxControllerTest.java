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

        String getUserResponse = "{\n" +
                "    \"userId\": \"2e3b11b0-07a4-4873-8de5-d2ae2eab25b1\",\n" +
                "    \"firstName\": \"Alex\",\n" +
                "    \"secondName\": \"Schmid\",\n" +
                "    \"createdOn\": \"2022-02-08T11:44:00.524\",\n" +
                "    \"email\": \"alex.schmid@gmail.com\",\n" +
                "    \"location\": {\n" +
                "        \"latitude\": 52.25742342295784,\n" +
                "        \"longitude\": 10.540583401747606\n" +
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

        mockMvc.perform(get("/user/location/2e3b11b0-07a4-4873-8de5-d2ae2eab25b1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(getUserResponse));
    }

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
}

package fr.burvelle.usersapi;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * UsersapiApplicationTests
 * Functional tests
 * @author Catherine BURVELLE
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
class UsersapiApplicationTests
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads()
    {
    }

    @Test
    public void testGetUsers() throws Exception
    {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Claude")))
                .andExpect(jsonPath("$[1].firstName", is("Jean")));

    }

    @Test
    public void testGetUser() throws Exception
    {
        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Claude")))
                .andExpect(jsonPath("$.lastName", is("DOE")))
                .andExpect(jsonPath("$.birthdate", is("2000-01-01")))
                .andExpect(jsonPath("$.gender", is("female")))
                .andExpect(jsonPath("$.phone", is("0102030406")))
                .andExpect(jsonPath("$.country", is("FRANCE")));

        mockMvc.perform(get("/user/2")).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(2)));

    }

    @Test
    public void testSetUser() throws Exception
    {
        // Create a complete user
        JSONObject jo = new JSONObject();
        jo.put("firstName", "firstTest1");
        jo.put("lastName", "LASTTEST1");
        jo.put("birthdate", "2000-01-01");
        jo.put("gender", "female");
        jo.put("phone", "0102030406");
        jo.put("country", "FRANCE");

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Firsttest1")))
                .andExpect(jsonPath("$.lastName", is("LASTTEST1")))
                .andExpect(jsonPath("$.birthdate", is("2000-01-01")))
                .andExpect(jsonPath("$.gender", is("female")))
                .andExpect(jsonPath("$.phone", is("0102030406")))
                .andExpect(jsonPath("$.country", is("FRANCE")));

        // Create a user with only mandatory info
        jo = new JSONObject();
        jo.put("firstName", "firstTest2");
        jo.put("lastName", "LASTTEST2");
        jo.put("birthdate", "2000-01-01");
        jo.put("country", "FRANCE");

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Firsttest2")))
                .andExpect(jsonPath("$.gender", nullValue()))
                .andExpect(jsonPath("$.phone", nullValue()));

        // Create a user in the alternative authorized country
        jo = new JSONObject();
        jo.put("firstName", "firstTest3");
        jo.put("lastName", "LASTTEST3");
        jo.put("birthdate", "2000-01-01");
        jo.put("gender", "female");
        jo.put("phone", "0102030406");
        jo.put("country", "Monaco");

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.country", is("Monaco")));

        // Try to create a user who is less than 18 year-old
        jo = new JSONObject();
        jo.put("firstName", "firstTest4");
        jo.put("lastName", "LASTTEST4");
        jo.put("birthdate", "2020-01-01");
        jo.put("gender", "female");
        jo.put("phone", "0102030406");
        jo.put("country", "FRANCE");
        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Age")));

        // Try to create a user from Italy
        jo = new JSONObject();
        jo.put("firstName", "firstTest5");
        jo.put("lastName", "LASTTEST5");
        jo.put("birthdate", "2000-01-01");
        jo.put("gender", "female");
        jo.put("phone", "0102030406");
        jo.put("country", "Italy");

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Country")));

        // Try to create a user without firstname
        jo = new JSONObject();
        jo.put("lastName", "LASTTEST6");
        jo.put("birthdate", "2000-01-01");
        jo.put("gender", "female");
        jo.put("phone", "0102030406");
        jo.put("country", "France");

        mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Firstname is missing or blank")));

    }

    @Test
    public void testUpdateUser() throws Exception
    {
        // Create a complete user
        JSONObject jo = new JSONObject();
        jo.put("lastName", "NEWNAME");

        mockMvc.perform(put("/user/1").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Claude")))
                .andExpect(jsonPath("$.lastName", is("NEWNAME")))
                .andExpect(jsonPath("$.birthdate", is("2000-01-01")))
                .andExpect(jsonPath("$.gender", is("female")))
                .andExpect(jsonPath("$.phone", is("0102030406")))
                .andExpect(jsonPath("$.country", is("FRANCE")));

        // Revert the DB to ensure any other tests are still OK
        jo = new JSONObject();
        jo.put("lastName", "DOE");
        mockMvc.perform(put("/user/1").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.lastName", is("DOE")));

    }
    
    @Test
    public void testDeleteUser() throws Exception
    {
        // Delete user id 1
        JSONObject jo = new JSONObject();
        jo.put("id", 1);
        mockMvc.perform(delete("/user/1").contentType(MediaType.APPLICATION_JSON).content(jo.toString()))
                .andExpect(status().isOk());

    }

}

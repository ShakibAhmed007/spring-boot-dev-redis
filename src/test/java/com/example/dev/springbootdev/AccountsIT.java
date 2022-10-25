package com.example.dev.springbootdev;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountsIT {

    @Autowired
    private TestRestTemplate template;

    private static final String ROLE_URL = "/accounts/getAllByRole?role=DEVELOPER";
    private static final String ADD_URL = "/accounts/add";

    @Test
    void getAllByRole_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(ROLE_URL, String.class);
        String expectedResponse = """
                [
                    {
                        "id": 17,
                        "userName": "Shakib Ahmed",
                        "password": "123",
                        "userEmail": "shakib2@gmail.com",
                        "userAge": 21,
                        "userRole": "DEVELOPER",
                        "createdOn": "2022-08-11T19:13:53.510943",
                        "updatedOn": "2022-08-11T19:13:53.510943",
                        "lastLogin": "2022-08-11T19:13:53.510943"
                    }
                ]
                """;
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), true);
    }

    @Test
    void add_basicScenario(){

        String reqBody = """
                {
                    "id": 0,
                    "userName": "Shakib Ahmed",
                    "password": "123",
                    "userEmail": "shakib711@gmail.com",
                    "userAge": 21,
                    "userRole": "ADMIN",
                    "createdOn": "2022-08-11T19:13:53.510943",
                    "updatedOn": "2022-08-11T19:13:53.510943",
                    "lastLogin": "2022-08-11T19:13:53.510943"
                }
                """;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        HttpEntity<String> httpEntity = new HttpEntity<String>(reqBody, httpHeaders);
        ResponseEntity<String> responseEntity = template.exchange(ADD_URL, HttpMethod.POST, httpEntity, String.class);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }




}

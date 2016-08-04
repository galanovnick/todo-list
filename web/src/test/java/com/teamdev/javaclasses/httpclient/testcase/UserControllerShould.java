package com.teamdev.javaclasses.httpclient.testcase;

import com.teamedv.javaclasses.httpclient.testunit.HttpClientTestUnit;
import com.teamedv.javaclasses.httpclient.testunit.impl.HttpClientTestUnitImpl;
import com.teamedv.javaclasses.httpclient.testunit.impl.Request;
import com.teamedv.javaclasses.httpclient.testunit.impl.Response;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.teamdev.javaclasses.httpclient.testcase.TodoListTestMethods.loginUser;
import static com.teamdev.javaclasses.httpclient.testcase.TodoListTestMethods.registerUser;
import static java.util.UUID.*;
import static org.apache.http.HttpStatus.SC_OK;

public class UserControllerShould {

    private final String baseUrl = "http://localhost:8080/api/";
    private final HttpClientTestUnit testUnit =
            new HttpClientTestUnitImpl(HttpClientBuilder.create().build());

    @Test
    public void handleSuccessfulUserRegistration() {
        registerUser(randomUUID().toString(), "123", "123", testUnit)
                .isStatusCodeEquals(SC_OK)
                .hasProperty("message", "User has been successfully registered.");

    }

    @Test
    public void handleUserWithDifferentPasswordsRegistration() {
        registerUser(randomUUID().toString(), "123", "321", testUnit)
                .isStatusCodeEquals(555)
                .hasProperty("message", "Passwords do not match.");
    }

    @Test
    public void handleUserWithEmptyFieldsRegistration() {
        registerUser("", "", "", testUnit)
                .isStatusCodeEquals(555)
                .hasProperty("message", "Fields cannot be empty.");
    }

    @Test
    public void handleDuplicatedUserRegistration() {
        String username = randomUUID().toString();
        registerUser(username, "123", "123", testUnit);

        registerUser(username, "123", "123", testUnit)
                .isStatusCodeEquals(555)
                .hasProperty("message", "User with such name already exists.");
    }

    @Test
    public void handleUnregisteredUserAuthentication() {
        loginUser(randomUUID().toString(), "123", testUnit)
                .isStatusCodeEquals(555)
                .hasProperty("message", "Invalid username or password.");
    }

    @Test
    public void handleUserWithEmptyFieldsAuthentication() {
        loginUser("", "", testUnit)
                .isStatusCodeEquals(555)
                .hasProperty("message", "Fields cannot be empty.");
    }

    @Test
    public void handleUserAuthentication() {
        String username = randomUUID().toString();

        registerUser(username, "123", "123", testUnit)
                .isStatusCodeEquals(SC_OK)
                .hasProperty("message", "User has been successfully registered.");

        loginUser(username, "123", testUnit)
                .isStatusCodeEquals(SC_OK)
                .hasProperty("token");
    }
}

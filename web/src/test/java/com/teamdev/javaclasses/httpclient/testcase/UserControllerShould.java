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

import static java.util.UUID.*;
import static org.apache.http.HttpStatus.SC_OK;

public class UserControllerShould {

    private final String baseUrl = "http://localhost:8080/api/";
    private final HttpClient client = HttpClientBuilder.create().build();
    private final HttpClientTestUnit testUnit = new HttpClientTestUnitImpl(client);

    @Test
    public void handleSuccessfulUserRegistration() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", randomUUID().toString()));
        params.add(new BasicNameValuePair("password", "12345"));
        params.add(new BasicNameValuePair("passwordConfirm", "12345"));

        Response response = testUnit.sendPost(new Request(params, baseUrl + "register"));
        response
                .isStatusCodeEquals(SC_OK)
                .hasProperty("message", "User has been successfully registered.");

    }

    @Test
    public void handleUserWithDifferentPasswordsRegistration() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", randomUUID().toString()));
        params.add(new BasicNameValuePair("password", "54321"));
        params.add(new BasicNameValuePair("passwordConfirm", "12345"));

        Response response = testUnit.sendPost(new Request(params, baseUrl + "register"));
        response
                .isStatusCodeEquals(555)
                .hasProperty("message", "Passwords do not match.");
    }

    @Test
    public void handleUserWithEmptyFieldsRegistration() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", ""));
        params.add(new BasicNameValuePair("password", ""));
        params.add(new BasicNameValuePair("passwordConfirm", ""));

        Response response = testUnit.sendPost(new Request(params, baseUrl + "register"));
        response
                .isStatusCodeEquals(555)
                .hasProperty("message", "Fields cannot be empty.");
    }

    @Test
    public void handleDuplicatedUserRegistration() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", "Vasya"));
        params.add(new BasicNameValuePair("password", "123"));
        params.add(new BasicNameValuePair("passwordConfirm", "123"));

        testUnit.sendPost(new Request(params, baseUrl + "register"));

        Response response = testUnit.sendPost(new Request(params, baseUrl + "register"));
        response
                .isStatusCodeEquals(555)
                .hasProperty("message", "User with such name already exists.");
    }

    @Test
    public void handleUnregisteredUserAuthentication() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", randomUUID().toString()));
        params.add(new BasicNameValuePair("password", "123"));

        Response response = testUnit.sendPost(new Request(params, baseUrl + "login"));
        response
                .isStatusCodeEquals(555)
                .hasProperty("message", "Invalid username or password.");
    }

    @Test
    public void handleUserWithEmptyFieldsAuthentication() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", ""));
        params.add(new BasicNameValuePair("password", ""));

        Response response = testUnit.sendPost(new Request(params, baseUrl + "login"));
        response
                .isStatusCodeEquals(555)
                .hasProperty("message", "Fields cannot be empty.");
    }

    @Test
    public void handleUserAuthentication() {
        List<NameValuePair> params = new ArrayList<>();
        String username = randomUUID().toString();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", "123"));
        params.add(new BasicNameValuePair("passwordConfirm", "123"));

        Response regUserResponse = testUnit.sendPost(new Request(params,
                "http://localhost:8080/api/register"));
        regUserResponse
                .isStatusCodeEquals(SC_OK)
                .hasProperty("message", "User has been successfully registered.");


        params.clear();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", "123"));

        Response loginResponse = testUnit.sendPost(new Request(params, baseUrl + "login"));
        loginResponse
                .isStatusCodeEquals(SC_OK)
                .isJson();
    }
}

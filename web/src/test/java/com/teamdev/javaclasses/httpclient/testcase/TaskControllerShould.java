package com.teamdev.javaclasses.httpclient.testcase;

import com.teamedv.javaclasses.httpclient.testunit.HttpClientTestUnit;
import com.teamedv.javaclasses.httpclient.testunit.impl.HttpClientTestUnitImpl;
import com.teamedv.javaclasses.httpclient.testunit.impl.Response;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;

import static com.teamdev.javaclasses.httpclient.testcase.TodoListTestMethods.*;
import static java.util.UUID.randomUUID;
import static org.apache.http.HttpStatus.SC_OK;

public class TaskControllerShould {

    private final HttpClientTestUnit testUnit =
            new HttpClientTestUnitImpl(HttpClientBuilder.create().build());

    private String token;

    @Before
    public void before() {
        String username = randomUUID().toString();
        registerUser(username, "123", "123", testUnit)
            .isStatusCodeEquals(SC_OK)
            .hasProperty("message", "User has been successfully registered.");

        Response response = loginUser(username, "123", testUnit);
        response
            .isStatusCodeEquals(SC_OK)
            .hasProperty("token");

        token = response.getProperty("token");
    }

    @Test
    public void handleTaskAddition() {
        createTask("Task description", token, testUnit)
            .isStatusCodeEquals(SC_OK)
            .isJson();
    }

    @Test
    public void handleTaskEdit() {
        Response createTaskResponse = createTask("Task description", token, testUnit);
        createTaskResponse
            .isStatusCodeEquals(SC_OK)
            .hasProperty("taskId");

        String taskId = createTaskResponse.getProperty("taskId");

        editTask(taskId, "New description", true, token, testUnit)
            .isStatusCodeEquals(SC_OK)
            .isJson();
    }

    @Test
    public void handleTaskRemoval() {
        Response createTaskResponse = createTask("Task description", token, testUnit);
        createTaskResponse
            .isStatusCodeEquals(SC_OK)
            .hasProperty("taskId");

        String taskId = createTaskResponse.getProperty("taskId");

        deleteTask(taskId, token, testUnit)
            .isStatusCodeEquals(SC_OK)
            .isJson();
    }

    @Test
    public void handleTasksGet() {
        createTask("Task description", token, testUnit)
            .isStatusCodeEquals(SC_OK)
            .hasProperty("taskId");

        getTaskList(token, testUnit);
    }
}

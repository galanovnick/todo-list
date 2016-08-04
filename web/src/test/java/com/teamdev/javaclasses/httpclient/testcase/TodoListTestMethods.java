package com.teamdev.javaclasses.httpclient.testcase;

import com.teamedv.javaclasses.httpclient.testunit.HttpClientTestUnit;
import com.teamedv.javaclasses.httpclient.testunit.impl.Request;
import com.teamedv.javaclasses.httpclient.testunit.impl.Response;
import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/*package*/ final class TodoListTestMethods {
    private TodoListTestMethods(){}

    private static final String baseUrl = "http://localhost:8080/api/";

    /*package*/ static Response registerUser(String username, String password,
                                              String passwordConfirm, HttpClientTestUnit testUnit) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("passwordConfirm", passwordConfirm));

        return testUnit.sendPost(new Request(params, baseUrl + "register"));
    }

    /*package*/ static Response loginUser(String username, String password, HttpClientTestUnit testUnit) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));

        return testUnit.sendPost(new Request(params, baseUrl + "login"));
    }

    /*package*/ static Response createTask(String description, String token, HttpClientTestUnit testUnit) {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("description", description));
        params.add(new BasicNameValuePair("token", token));

        return testUnit.sendPost(new Request(params, baseUrl + "task"));
    }

    /*package*/ static Response editTask(String taskId, String newDescription, boolean isDone,
                                          String token, HttpClientTestUnit testUnit) {

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("taskId", taskId));
        params.add(new BasicNameValuePair("description", newDescription));
        params.add(new BasicNameValuePair("isDone", String.valueOf(isDone)));
        params.add(new BasicNameValuePair("token", token));

        return testUnit.sendPut(new Request(params, baseUrl + "task"));
    }

    /*package*/ static Response deleteTask(String taskId, String token, HttpClientTestUnit testUnit) {
        return testUnit.sendDelete(new Request(
                String.format("%stask?taskId=%s&token=%s", baseUrl, taskId, token)
        ));
    }

    /*package*/ static Response getTaskList(String token, HttpClientTestUnit testUnit) {
        return testUnit.sendDelete(new Request(
                String.format("%stask?token=%s", baseUrl, token)
        ));
    }
}

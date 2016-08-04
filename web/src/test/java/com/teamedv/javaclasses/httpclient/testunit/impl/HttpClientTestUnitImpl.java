package com.teamedv.javaclasses.httpclient.testunit.impl;

import com.google.gson.JsonObject;
import com.teamedv.javaclasses.httpclient.testunit.HttpClientTestUnit;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

import static org.apache.http.HttpHeaders.USER_AGENT;
import static org.junit.Assert.fail;

public class HttpClientTestUnitImpl implements HttpClientTestUnit {

    private final HttpClient httpClient;

    public HttpClientTestUnitImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Response sendPost(Request request) {
        HttpPost post = new HttpPost(request.getUrl());
        post.setHeader("User-Agent", USER_AGENT);
        try {
            post.setEntity(new UrlEncodedFormEntity(request.getParams()));
            return new Response(httpClient.execute(post));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception: " + e.getClass().getName());
        }
        return null;
    }

    @Override
    public Response sendPut(Request request) {
        HttpPut put = new HttpPut(request.getUrl());
        final JsonObject json = new JsonObject();
        request.getParams().forEach(param -> json.addProperty(param.getName(), param.getValue()));
        try {
            put.setEntity(new StringEntity(json.toString()));
            return new Response(httpClient.execute(put));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception: " + e.getClass().getName());
        }
        return null;
    }

    @Override
    public Response sendGet(Request request) {
        HttpGet get = new HttpGet(request.getUrl());
        try {
            return new Response(httpClient.execute(get));
        } catch (IOException e) {
            e.printStackTrace();
            fail("Unexpected exception: " + e.getClass().getName());
        }
        return null;
    }

    @Override
    public Response sendDelete(Request request) {
        HttpDelete delete = new HttpDelete(request.getUrl());
        try {
            return new Response(httpClient.execute(delete));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception: " + e.getClass().getName());
        }
        return null;
    }
}

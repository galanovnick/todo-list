package com.teamedv.javaclasses.httpclient.testunit.impl;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;

public class Request {

    private final List<NameValuePair> params;
    private final String url;

    public Request(List<NameValuePair> params, String url) {
        this.params = params;
        this.url = url;
    }

    public Request(String url) {
        this.params = new ArrayList<>();
        this.url = url;
    }

    /*package*/ List<NameValuePair> getParams() {
        return params;
    }

    /*package*/ String getUrl() {
        return url;
    }
}

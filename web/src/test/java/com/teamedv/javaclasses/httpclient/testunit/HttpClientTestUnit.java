package com.teamedv.javaclasses.httpclient.testunit;

import com.teamedv.javaclasses.httpclient.testunit.impl.Request;
import com.teamedv.javaclasses.httpclient.testunit.impl.Response;

public interface HttpClientTestUnit {

    Response sendPost(Request request);
    Response sendGet(Request request);
    Response sendDelete(Request request);
}

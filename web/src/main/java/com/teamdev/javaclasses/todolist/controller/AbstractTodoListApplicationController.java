package com.teamdev.javaclasses.todolist.controller;

import com.google.common.base.Splitter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.teamdev.javaclasses.todolist.handler.HandlerRegistry;
import com.teamdev.javaclasses.todolist.handler.UrlMethodPair;
import com.teamdev.javaclasses.todolist.content.ResultWriter;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static com.teamdev.javaclasses.todolist.handler.HttpRequestMethod.GET;
import static com.teamdev.javaclasses.todolist.handler.HttpRequestMethod.PUT;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

/*package*/ abstract class AbstractTodoListApplicationController {

    /*package*/ void handleGet(String url, HandlerRegistry handlerRegistry) {
        UrlMethodPair createChat = new UrlMethodPair(url, GET);
        handlerRegistry.register(createChat, ((request, response) -> {
            try {
                System.out.println("forward to \"/\"");
                request.getRequestDispatcher("/").forward(request, response);
                return null;
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }));
    }

    /*package*/ ResultWriter authenticationRequiredErrorWriter() {
        return resp -> {
            try {
                resp.getWriter().write("Authentication required.");
                resp.setStatus(SC_FORBIDDEN);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        };
    }

    /*package*/ Map<String, String> getPutRequestParams(HttpServletRequest request) {
        if (!request.getMethod().equals(PUT.name())) {
            throw new IllegalStateException("This method supports only \"PUT\" method.");
        } else {
            BufferedReader br = null;
            StringBuilder resultString = new StringBuilder();
            try {
                br = new BufferedReader(new InputStreamReader(request.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    resultString.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return Splitter.on("&").withKeyValueSeparator("=").split(resultString.toString());
        }
    }
}
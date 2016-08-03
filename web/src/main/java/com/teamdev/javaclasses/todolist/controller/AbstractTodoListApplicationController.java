package com.teamdev.javaclasses.todolist.controller;

import com.teamdev.javaclasses.todolist.handler.HandlerRegistry;
import com.teamdev.javaclasses.todolist.handler.UrlMethodPair;
import com.teamdev.javaclasses.todolist.content.ResultWriter;

import java.io.IOException;

import static com.teamdev.javaclasses.todolist.handler.HttpRequestMethod.GET;
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
}
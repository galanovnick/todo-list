package com.teamdev.javaclasses.todolist.handler;

import com.teamdev.javaclasses.todolist.content.ResultWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Handler {

    ResultWriter processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException;
}

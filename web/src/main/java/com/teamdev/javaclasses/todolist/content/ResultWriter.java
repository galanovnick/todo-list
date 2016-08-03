package com.teamdev.javaclasses.todolist.content;

import javax.servlet.http.HttpServletResponse;

public interface ResultWriter {
    void write(HttpServletResponse response);
}

package com.teamdev.javaclasses.todolist.controller;

import com.teamdev.javaclasses.service.InvalidDescriptionException;
import com.teamdev.javaclasses.service.TaskService;
import com.teamdev.javaclasses.service.UserService;
import com.teamdev.javaclasses.service.dto.AuthenticationTokenDto;
import com.teamdev.javaclasses.service.impl.TaskServiceImpl;
import com.teamdev.javaclasses.service.impl.UserServiceImpl;
import com.teamdev.javaclasses.todolist.content.JsonContent;
import com.teamdev.javaclasses.todolist.content.JsonResultWriter;
import com.teamdev.javaclasses.todolist.handler.HandlerRegistry;
import com.teamdev.javaclasses.todolist.handler.UrlMethodPair;
import com.teamedv.javaclasses.todolist.entity.Task;
import com.teamedv.javaclasses.todolist.entity.tiny.TaskId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.teamdev.javaclasses.todolist.handler.HttpRequestMethod.*;

public class TaskController extends AbstractTodoListApplicationController {

    private static TaskController instance;

    private final TaskService taskService = TaskServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    private TaskController(HandlerRegistry handlerRegistry) {
        registerTaskPost(handlerRegistry);
        registerTaskGet(handlerRegistry);
        registerTaskDelete(handlerRegistry);
        registerTaskPut(handlerRegistry);
    }

    private void registerTaskPut(HandlerRegistry handlerRegistry) {
        UrlMethodPair urlMethodPair = new UrlMethodPair("/api/task", PUT);
        handlerRegistry.register(urlMethodPair, ((request, response) -> {
            System.out.println(request.getParameter("description"));
            Optional<AuthenticationTokenDto> token =
                    userService.checkAuthentication(
                            new AuthenticationTokenDto(request.getParameter("token")));

            if (token.isPresent()) {
                Task task = new Task(request.getParameter("description"), new UserId(-1L));
                task.setDone(Boolean.parseBoolean(request.getParameter("isDone")));

                try {
                    taskService.editTask(
                            new TaskId(Long.parseLong(request.getParameter("taskId"))), task);
                } catch (InvalidDescriptionException e) {
                    JsonContent result = new JsonContent();
                    result.put("message", e.getMessage());
                    return new JsonResultWriter(result, 555);
                }

                JsonContent result = new JsonContent();
                result.put("message", "Task has been successfully updated.");
                return new JsonResultWriter(result, 200);
            } else {
                return authenticationRequiredErrorWriter();
            }
        }));
    }

    private void registerTaskDelete(HandlerRegistry handlerRegistry) {
        UrlMethodPair urlMethodPair = new UrlMethodPair("/api/task", DELETE);
        handlerRegistry.register(urlMethodPair, ((request, response) -> {
            Optional<AuthenticationTokenDto> token =
                    userService.checkAuthentication(
                            new AuthenticationTokenDto(request.getParameter("token")));

            if (token.isPresent()) {
                taskService.delete(new TaskId(
                        Long.valueOf(request.getParameter("taskId"))));
                JsonContent result = new JsonContent();
                result.put("message", "Task has been successfully deleted.");
                return new JsonResultWriter(result, 200);
            } else {
                return authenticationRequiredErrorWriter();
            }
        }));
    }

    private void registerTaskGet(HandlerRegistry handlerRegistry) {
        UrlMethodPair urlMethodPair = new UrlMethodPair("/api/task", GET);
        handlerRegistry.register(urlMethodPair, ((request, response) -> {

            Optional<AuthenticationTokenDto> token =
                    userService.checkAuthentication(
                            new AuthenticationTokenDto(request.getParameter("token")));

            if (token.isPresent()) {
                List<String> tasks = taskService.getByUser(token.get().getUserId()).stream()
                        .map(task -> {
                            JsonContent taskContent = new JsonContent();
                            taskContent.put("description", task.getDescription());
                            taskContent.put("isDone", String.valueOf(task.isDone()));
                            taskContent.put("taskId", task.getTaskId().value().toString());
                            return taskContent.getResultContent();
                        }).collect(Collectors.toList());

                JsonContent result = new JsonContent();
                result.put("tasks", tasks.toArray(new String[tasks.size()]));

                return new JsonResultWriter(result, 200);
            } else {
                return authenticationRequiredErrorWriter();
            }
        }));
    }

    private void registerTaskPost(HandlerRegistry handlerRegistry) {
        UrlMethodPair urlMethodPair = new UrlMethodPair("/api/task", POST);
        handlerRegistry.register(urlMethodPair, ((request, response) -> {

            Optional<AuthenticationTokenDto> token =
                    userService.checkAuthentication(
                            new AuthenticationTokenDto(request.getParameter("token")));

            if (token.isPresent()) {
                TaskId createdTaskId = null;
                try {
                    createdTaskId = taskService.add(
                            new Task(request.getParameter("description"),
                                token.get().getUserId()));
                } catch (InvalidDescriptionException e) {
                    JsonContent result = new JsonContent();
                    result.put("message", e.getMessage());
                    return new JsonResultWriter(result, 555);
                }
                JsonContent result = new JsonContent();
                result.put("taskId", createdTaskId.value().toString());

                return new JsonResultWriter(result, 200);
            } else {
                return authenticationRequiredErrorWriter();
            }
        }));
    }

    public static void instantiate(HandlerRegistry handlerRegistry) {
        if (instance == null) {
            instance = new TaskController(handlerRegistry);
        }
    }

}

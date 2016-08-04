package com.teamdev.javaclasses.todolist.controller;

import com.teamdev.javaclasses.service.AuthenticationException;
import com.teamdev.javaclasses.service.InvalidUserDataException;
import com.teamdev.javaclasses.service.UserService;
import com.teamdev.javaclasses.service.dto.AuthenticationTokenDto;
import com.teamdev.javaclasses.service.dto.RegistrationDto;
import com.teamdev.javaclasses.service.impl.UserServiceImpl;
import com.teamdev.javaclasses.todolist.handler.HandlerRegistry;
import com.teamdev.javaclasses.todolist.handler.UrlMethodPair;
import com.teamdev.javaclasses.todolist.content.JsonContent;
import com.teamdev.javaclasses.todolist.content.JsonResultWriter;
import com.teamedv.javaclasses.todolist.entity.tiny.Password;
import com.teamedv.javaclasses.todolist.entity.tiny.Email;

import java.util.Optional;

import static com.teamdev.javaclasses.todolist.controller.ControllerConstants.*;
import static com.teamdev.javaclasses.todolist.handler.HttpRequestMethod.GET;
import static com.teamdev.javaclasses.todolist.handler.HttpRequestMethod.POST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

public class UserController extends AbstractTodoListApplicationController {

    private static UserController instance;

    private final UserService userService = UserServiceImpl.getInstance();

    private UserController(HandlerRegistry handlerRegistry) {
        registerRegistrationGet(handlerRegistry);
        registerRegistrationPost(handlerRegistry);

        registerLoginGet(handlerRegistry);
        registerLoginPost(handlerRegistry);

        registerUsernamePost(handlerRegistry);
        registerUsernameGet(handlerRegistry);
    }

    private void registerRegistrationPost(HandlerRegistry handlerRegistry) {
        UrlMethodPair postUrlMethodPair = new UrlMethodPair("/api/register", POST);

        handlerRegistry.register(postUrlMethodPair, ((request, response) -> {

            JsonContent result = new JsonContent();

            RegistrationDto regDto = new RegistrationDto(
                    new Email(request.getParameter(EMAIL_PARAMETER)),
                    new Password(request.getParameter(PASSWORD_PARAMETER)),
                    new Password(request.getParameter(PASSWORD_CONFIRM_PARAMETER))
            );

            try {
                userService.registerUser(regDto);
            } catch (InvalidUserDataException e) {
                result.put(MESSAGE_PARAMETER, e.getMessage());
                return new JsonResultWriter(result, 555);
            }
            result.put(MESSAGE_PARAMETER, "User has been successfully registered.");
            return new JsonResultWriter(result, SC_OK);
        }));
    }
    private void registerRegistrationGet(HandlerRegistry handlerRegistry) {
        handleGet("/api/register", handlerRegistry);
    }
    private void registerLoginPost(HandlerRegistry handlerRegistry) {
        UrlMethodPair postUrlMethodPair = new UrlMethodPair("/api/login", POST);
        handlerRegistry.register(postUrlMethodPair, ((request, response) -> {
            JsonContent result = new JsonContent();
            AuthenticationTokenDto token;

            try {
                token = userService.login(new Email(request.getParameter(EMAIL_PARAMETER)),
                        new Password(request.getParameter(PASSWORD_PARAMETER)));
            } catch (AuthenticationException e) {
                result.put(MESSAGE_PARAMETER, e.getMessage());
                return new JsonResultWriter(result, 555);
            }
            result.put(TOKEN_PARAMETER, token.getToken());
            return new JsonResultWriter(result, 200);
        }));
    }

    private void registerLoginGet(HandlerRegistry handlerRegistry) {
        UrlMethodPair postUrlMethodPair = new UrlMethodPair("/api/login", GET);
        handlerRegistry.register(postUrlMethodPair, ((request, response) -> {
            Optional<AuthenticationTokenDto> token =
                    userService.checkAuthentication(
                            new AuthenticationTokenDto(request.getParameter("token")));

            if (token.isPresent()) {
                String userEmail = userService.getUser(token.get().getUserId()).getEmail();
                JsonContent result = new JsonContent();
                result.put(EMAIL_PARAMETER, userEmail);
                return new JsonResultWriter(result, 200);
            }
            else {
                return authenticationRequiredErrorWriter();
            }
        }));
    }

    private void registerUsernamePost(HandlerRegistry handlerRegistry) {
        UrlMethodPair postUsernameRequest
                = new UrlMethodPair("/api/username", POST);
        handlerRegistry.register(postUsernameRequest, ((request, response) -> {
            Optional<AuthenticationTokenDto> token = userService.checkAuthentication(
                    new AuthenticationTokenDto(request.getParameter(TOKEN_PARAMETER))
            );

            if (token.isPresent()) {
                JsonContent result = new JsonContent();
                result.put(EMAIL_PARAMETER,
                        userService.getUser(token.get().getUserId())
                                .getEmail());
                return new JsonResultWriter(result, 200);
            } else {
                return authenticationRequiredErrorWriter();
            }
        }));
    }

    private void registerUsernameGet(HandlerRegistry handlerRegistry) {
        handleGet("/api/username", handlerRegistry);
    }

    public static void instantiate(HandlerRegistry handlerRegistry) {
        if (instance == null) {
            instance = new UserController(handlerRegistry);
        }
    }
}

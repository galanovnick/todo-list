package com.teamdev.javaclasses.service;

import com.teamdev.javaclasses.service.dto.AuthenticationTokenDto;
import com.teamdev.javaclasses.service.dto.RegistrationDto;
import com.teamdev.javaclasses.service.impl.UserServiceImpl;
import com.teamedv.javaclasses.todolist.entity.tiny.Email;
import com.teamedv.javaclasses.todolist.entity.tiny.Password;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UserServiceShould {
    private final UserService userService = UserServiceImpl.getInstance();

    private final RegistrationDto user = new RegistrationDto(
            new Email("vasya@mail.com"),
            new Password("123"),
            new Password("123"));

    private UserId userId;

    @Before
    public void before() throws InvalidUserDataException {
        if (userService.getAllTokens().size() > 0
                || userService.getAllRegisteredUsers().size() > 0) {
            fail("Repositories have to be empty.");
        }
        userId = userService.registerUser(user);
    }

    @After
    public void after() {
        userService.deleteUser(userId);
    }

    @Test
    public void registerUser() {
        assertTrue("Failed user registration.", userService.isUserRegistered(userId));
    }

    @Test
    public void authenticateUser() {
        AuthenticationTokenDto token = null;
        try {
            token = userService.login(
                    new Email(this.user.getEmail()),
                    new Password(this.user.getPassword()));
        } catch (AuthenticationException e) {
            fail("Failed user addition.");
        }

        boolean actual = userService.checkAuthentication(token).isPresent();

        userService.logout(token);

        assertTrue("Failed user authentication", actual);
    }

    @Test(expected = InvalidUserDataException.class)
    public void notRegisterDuplicatedUser() throws InvalidUserDataException {

        userService.registerUser(user);

        fail("Expected InvalidUserDataException.");
    }

    @Test(expected = InvalidUserDataException.class)
    public void notRegisterUserWithDifferentPasswords() throws InvalidUserDataException {
        final RegistrationDto user = new RegistrationDto(
                new Email("Vasya"),
                new Password("123"),
                new Password("321"));

        userService.registerUser(user);

        fail("Expected InvalidUserDataException.");
    }

    @Test(expected = AuthenticationException.class)
    public void notAuthenticateInvalidUser() throws AuthenticationException {
        userService.login(
                new Email(randomUUID().toString()),
                new Password(randomUUID().toString()));

        fail("Expected AuthenticationException.");
    }
}

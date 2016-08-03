package com.teamdev.javaclasses.service.impl;

import com.teamdev.javaclasses.service.AuthenticationException;
import com.teamdev.javaclasses.service.InvalidUserDataException;
import com.teamdev.javaclasses.service.UserService;
import com.teamdev.javaclasses.service.dto.AuthenticationTokenDto;
import com.teamdev.javaclasses.service.dto.RegistrationDto;
import com.teamdev.javaclasses.service.dto.UserDto;
import com.teamedv.javaclasses.todolist.entity.tiny.Password;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;
import com.teamedv.javaclasses.todolist.entity.tiny.Username;

import java.util.Collection;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    public static UserService getInstance() {
        return null;
    }

    @Override
    public UserId registerUser(RegistrationDto user) throws InvalidUserDataException {
        return null;
    }

    @Override
    public UserDto getUser(UserId userId) {
        return null;
    }

    @Override
    public boolean isUserRegistered(UserId id) {
        return false;
    }

    @Override
    public void deleteUser(UserId id) {

    }

    @Override
    public Collection<UserDto> getAllRegisteredUsers() {
        return null;
    }

    @Override
    public void logout(AuthenticationTokenDto user) {

    }

    @Override
    public Collection<AuthenticationTokenDto> getAllTokens() {
        return null;
    }

    @Override
    public AuthenticationTokenDto login(Username username, Password password) throws AuthenticationException {
        return null;
    }

    @Override
    public Optional<AuthenticationTokenDto> checkAuthentication(AuthenticationTokenDto token) {
        return null;
    }
}

package com.teamdev.javaclasses.service;

import com.teamdev.javaclasses.service.dto.AuthenticationTokenDto;
import com.teamdev.javaclasses.service.dto.RegistrationDto;
import com.teamdev.javaclasses.service.dto.UserDto;
import com.teamedv.javaclasses.todolist.entity.tiny.Password;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;
import com.teamedv.javaclasses.todolist.entity.tiny.Username;

import java.util.Collection;
import java.util.Optional;

public interface UserService {
    UserId registerUser(RegistrationDto user) throws InvalidUserDataException;

    UserDto getUser(UserId userId);

    boolean isUserRegistered(UserId id);

    void deleteUser(UserId id);

    Collection<UserDto> getAllRegisteredUsers();

    void logout(AuthenticationTokenDto user);

    Collection<AuthenticationTokenDto> getAllTokens();

    AuthenticationTokenDto login(Username username, Password password) throws AuthenticationException;

    Optional<AuthenticationTokenDto> checkAuthentication(AuthenticationTokenDto token);
}

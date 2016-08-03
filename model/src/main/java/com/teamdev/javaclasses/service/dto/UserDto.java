package com.teamdev.javaclasses.service.dto;

import com.teamedv.javaclasses.todolist.entity.tiny.Username;

public class UserDto {

    private String username;

    public UserDto(Username username) {
        this.username = username.value();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return username != null ? username.equals(userDto.username) : userDto.username == null;

    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}

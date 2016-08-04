package com.teamdev.javaclasses.service.dto;

import com.teamedv.javaclasses.todolist.entity.tiny.Email;

public class UserDto {

    private String email;

    public UserDto(Email email) {
        this.email = email.value();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        return email != null ? email.equals(userDto.email) : userDto.email == null;

    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}

package com.teamdev.javaclasses.service.dto;

import com.teamedv.javaclasses.todolist.entity.tiny.Email;
import com.teamedv.javaclasses.todolist.entity.tiny.Password;

public class RegistrationDto {

    private String email;
    private String password;
    private String passwordConfirm;

    public RegistrationDto(Email email, Password password, Password passwordConfirm) {
        this.email = email.value();
        this.password = password.value();
        this.passwordConfirm = passwordConfirm.value();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrationDto that = (RegistrationDto) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        return passwordConfirm != null ? passwordConfirm.equals(that.passwordConfirm) : that.passwordConfirm == null;

    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (passwordConfirm != null ? passwordConfirm.hashCode() : 0);
        return result;
    }
}

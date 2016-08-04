package com.teamedv.javaclasses.todolist.entity;

import com.teamedv.javaclasses.todolist.entity.tiny.Email;
import com.teamedv.javaclasses.todolist.entity.tiny.Password;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;

public class User implements Entity<UserId> {

    private Long id = 0L;
    private String username;
    private String password;

    public User(Email email, Password password) {
        this.username = email.value();
        this.password = password.value();
    }

    @Override
    public UserId getId() {
        return new UserId(id);
    }

    public void setId(UserId id) {
        this.id = id.value();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

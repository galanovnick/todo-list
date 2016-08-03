package com.teamedv.javaclasses.todolist.entity;

import com.teamedv.javaclasses.todolist.entity.tiny.AuthenticationTokenId;
import com.teamedv.javaclasses.todolist.entity.tiny.UserId;

public class AuthenticationToken implements Entity<AuthenticationTokenId> {
    private Long id = 0L;
    private String token;
    private Long userId;

    public AuthenticationToken(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }

    public AuthenticationToken(Long id, String token, Long userId) {
        this.id = id;
        this.token = token;
        this.userId = userId;
    }

    @Override
    public AuthenticationTokenId getId() {
        return new AuthenticationTokenId(id);
    }

    public void setId(AuthenticationTokenId id) {
        this.id = id.value();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserId getUserId() {
        return new UserId(userId);
    }

    public void setUserId(UserId userId) {
        this.userId = userId.value();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuthenticationToken that = (AuthenticationToken) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}

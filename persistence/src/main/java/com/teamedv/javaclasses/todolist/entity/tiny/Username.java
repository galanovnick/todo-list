package com.teamedv.javaclasses.todolist.entity.tiny;

public class Username {

    private final String username;

    public Username(String username) {
        this.username = username;
    }

    public String value() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Username username = (Username) o;

        return this.username != null ? this.username.equals(username.username) : username.username == null;

    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }
}

package com.teamedv.javaclasses.todolist.entity.tiny;

public class Email {

    private final String email;

    public Email(String email) {
        this.email = email;
    }

    public String value() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        return this.email != null ? this.email.equals(email.email) : email.email == null;

    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }
}
